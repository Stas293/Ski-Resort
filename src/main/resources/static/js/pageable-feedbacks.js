let urlPath;

const Paths = {
    EVENT_ARTICLE: '/api/v1/feedback/events',
    RESORT_ARTICLE: '/api/v1/feedback/resorts'
}

$(function (){
    const id = $('#id').val();
    let bodyClass = $('body')[0].getAttribute('id').toString();
    urlPath = Paths[bodyClass.toUpperCase()] + `/${id}`;
    wizard(urlPath);
});

class Feedback {
    constructor(fullName, message, dateCreated, rating) {
        this.fullName = fullName;
        this.message = message;
        this.dateCreated = dateCreated;
        this.rating = rating;
    }

    static from = function (rowData) {
        return new Feedback(
            rowData.fullName,
            rowData.message,
            rowData.dateCreated,
            rowData.rating);
    }
}

const makeRow = (rowData) => {
    const feedback = Feedback.from(rowData);
    console.log(feedback);
    let tableRow = document.createElement('tr');

    let tableData = document.createElement('td');
    tableData.appendChild(document.createTextNode(feedback.fullName));
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(document.createTextNode(feedback.message));
    tableData.setAttribute('id', 'message-td');
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(document.createTextNode(feedback.dateCreated));
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(document.createTextNode(feedback.rating));
    tableRow.appendChild(tableData);

    return tableRow;
}