let urlPath = '/api/v1/users/admin';

$(function () {
    wizard(urlPath);
});

class User {
    constructor(username, email, firstName, lastName, roles, enabled) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.enabled = enabled;
    }

    static from = function (rowData) {
        return new User(
            rowData.username,
            rowData.email,
            rowData.firstName,
            rowData.lastName,
            rowData.roles,
            rowData.enabled);
    }
}

const makeRow = (rowData) => {
    const user = User.from(rowData);
    console.log(user);
    let tableRow = document.createElement('tr');

    let tableData = document.createElement('td');
    let anchor = document.createElement('a');
    let value = `/user/${rowData.id}/admin`;
    anchor.setAttribute('href', value);
    anchor.appendChild(document.createTextNode(rowData.username));

    tableData.appendChild(anchor);
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    anchor = document.createElement('a');
    anchor.setAttribute('href', `mailto:${user.email}`);
    anchor.appendChild(document.createTextNode(user.email));
    tableData.appendChild(anchor);
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(document.createTextNode(user.firstName));
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(document.createTextNode(user.lastName));
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    let roleArr = user.roles
        .map(e => e.name)
        .join(', ');

    tableData.appendChild(document.createTextNode(roleArr));
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(document.createTextNode(user.enabled));
    tableRow.appendChild(tableData);
    return tableRow;
}