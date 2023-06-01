let urlPath;

const Paths = {
    DASHBOARD_RESORT: '/api/v1/cities',
    DASHBOARD_CITY: '/api/v1/country',
    DASHBOARD_COUNTRY: '/api/v1/continent'
}

window.onload = function () {
    run();
}

function run () {
    $("#choices").autocomplete({
        source: function (request, response) {
            let bodyClass = $('body')[0].getAttribute('id').toString();
            urlPath = Paths[bodyClass.toUpperCase()];
            $.get(`${urlPath}`, {search: request.term.trim()}, function (data) {
                console.log(data);
                const cities = data.map(function (data) {
                    let myVal = findValue(data)[bodyClass.toUpperCase()];
                    console.log(myVal);
                    return {
                        value: myVal,
                        id: data.id
                    };
                });

                response(cities);
            });
        },

        minLength: 2,
        select: function (event, ui) {
            const author = ui.item;
            console.log(author);
            addAuthorToList(author);
            return false;
        }
    });

    $("#items-list").on("click", ".btn-close", function () {
        $(this).parent().remove();
        let authors = $("#items-list li").map(function () {
            return $(this).data("id");
        }).get().join(",");
        $("#items-input").val(authors);
    });
}

function addAuthorToList(author) {
    if ($("#items-list li[data-id='" + author.id + "']").length > 0) {
        return;
    }
    $("#choices").val("");
    $("#items-list").append("<li data-id='" + author.id + "'>" + author.value + " <button class='btn-close' aria-label='Close'>Delete</button></li>");

    const authors = $("#items-list li").map(function () {
        return $(this).data("id");
    }).get().join(",");
    $("#items-input").val(authors);
}


function findValue(body) {
    return {
        DASHBOARD_RESORT: `${body.city}, ${body.country}, ${body.continent}`,
        DASHBOARD_CITY: `${body.country}, ${body.continent}`,
        DASHBOARD_COUNTRY: `${body.continent}`,
    }
}