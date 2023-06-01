let defaultSizeValue = 5;
let defaultStringValue = '';
let size, sorting = 'ASC', search, sorting_desc, sorting_asc;

function ajaxJS(url, callback) {
    console.log(url);
    let indexFirst = url.indexOf("?");
    let lastIndexOf = url.lastIndexOf("?");
    if (indexFirst !== lastIndexOf) {
        url = url.substring(0, lastIndexOf) + '&' + url.substring(lastIndexOf + 1);
    }
    console.log(url);
    $.ajax({
        url: url,
        type: 'GET',
        dataType: "json",
        success: function (data) {
            console.log(data);
            callback(data);
        },
        error: function (xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
}

const clearTextFields = function () {
    let querySelector = document.querySelector("#search");
    querySelector.setAttribute("value", "");
};

const clear = function (tag) {
    while (tag.firstChild) {
        tag.removeChild(tag.firstChild);
    }
};

const makeHTMLRows = function (data, callback) {
    const documentFragment = document.createDocumentFragment();
    data.forEach(function (rowData, index) {
        documentFragment.appendChild(makeRow(rowData, index, callback));
    });
    return documentFragment;
};

function makeLeft(div, number) {
    const anchor = document.createElement('a');
    anchor.setAttribute('class', 'page-item');
    anchor.appendChild(document.createTextNode('<<'));
    anchor.onclick = function () {
        let pathToRequest = `${urlPath}?page=${number - 1}&limit=${size.value}&search=${search.value}&sorting=${sorting}`;
        showResults(pathToRequest);
    };
    div.appendChild(anchor);
}

function makeRight(div, number) {
    const anchor = document.createElement('a');
    anchor.setAttribute('class', 'page-item');
    anchor.appendChild(document.createTextNode('>>'));
    anchor.onclick = function () {
        let pathToRequest = `${urlPath}?page=${number + 1}&limit=${size.value}&search=${search.value}&sorting=${sorting}`;
        showResults(pathToRequest);
    };
    div.appendChild(anchor);
}

const showResults = function (url, callback) {
    ajaxJS(url, function (response) {
        const tbody = document.getElementById('cards');
        const div = document.getElementById('page-navigation');
        clear(div);
        clear(tbody);
        const pages = response.totalPages;
        const number = response.number;
        console.log(response);
        tbody.appendChild(makeHTMLRows(response.content, callback));
        if (!response.first) makeLeft(div, number);
        div.appendChild(makePageNavigation(pages, number));
        if (!response.last) makeRight(div, number);
    });
};

const makePages = function (i, number) {
    const anchor = document.createElement('a');
    anchor.setAttribute('class', 'page-item');
    if (i===number){
        anchor.classList.add('active')
    }
    anchor.appendChild(document.createTextNode(i + 1));
    anchor.onclick = function () {
        let pathToRequest = `${urlPath}?page=${i}&limit=${size.value}&search=${search.value}&sorting=${sorting}`;
        showResults(pathToRequest);
    };
    return anchor;
};

const makePageNavigation = function (pages, number) {
    const documentFragment = document.createDocumentFragment();
    for (let i = 0; i < pages; i++)
        documentFragment.appendChild(makePages(i, number));
    return documentFragment;
};

const addListeners = function (url) {
    sorting_desc.onclick = function () {
        sorting = sorting_desc.value;
        let pathToRequest = `${url}?limit=${size.value}&search=${search.value}&sorting=${sorting}&sortBy=${sortBy}`;
        showResults(pathToRequest);
    };
    sorting_asc.onclick = function () {
        sorting = sorting_asc.value;
        let pathToRequest = `${url}?limit=${size.value}&search=${search.value}&sorting=${sorting}&sortBy=${sortBy}`;
        showResults(pathToRequest);
    };
    size.onclick = function () {
        if (size.value < 2) {
            size.value = 2;
        } else if (size.value > 8) {
            size.value = 8;
        }
        let pathToRequest = `${url}?limit=${size.value}&search=${search.value}&sorting=${sorting}&sortBy=${sortBy}`;
        showResults(pathToRequest);
    };
    search.onkeyup = function () {
        let pathToRequest = `${url}?limit=${size.value}&search=${search.value}&sorting=${sorting}`;
        showResults(pathToRequest);
    };
};

const toRequest = function (url) {
    let pathToRequest = `${url}?limit=${size.value}&search=${search.value}&sorting=${sorting}&sortBy=${sortBy}`;
    showResults(pathToRequest);
};

const wizard = function (urlPath, callback) {
    size = document.querySelector("#size");
    search = document.querySelector("#search");
    sorting_desc = document.querySelector("#desc");
    sorting_asc = document.querySelector("#asc");
    sortBy = "";
    addListeners(urlPath);
    toRequest(urlPath);
};

const setSize = () => {
    size = document.querySelector(`#size`);
    size.value = defaultSizeValue;
}


