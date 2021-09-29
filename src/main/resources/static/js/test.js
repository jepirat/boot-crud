const url = '/users';
let users = []
let buttonsIdsEdit = []

class User {
    id
    login
    firstName
    lastName
    password
    roles = []

    constructor(id, login, firstName, lastName, password, roles) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }
};

async function sendRequest(url) {
    let row = ['id', 'login', 'firstName', 'lastName', 'password', 'roles']
    let response = await fetch(url)
    let js = await response.json()

    for (let j = 0; j < js.length; j++) {
        let fields = []
        for (let i = 0; i < row.length; i++) {
            fields.push(js[j][row[i]])
        }
        let user = new User()
        user.id = fields[0]
        user.login = fields[1]
        user.firstName = fields[2]
        user.lastName = fields[3]
        user.password = fields[4]
        user.roles = fields[5]
        users.push(user)
    }
    setRowsAndButtons()
    scanUsers()
}
sendRequest(url)

const timer = ms => new Promise(res => setTimeout(res, ms))

async function scanUsers() {
    await sendRequest
    let userLength = users.length
    while (true) {
        if (userLength != users.length) {
            userLength = users.length
            $('#usersTable > tbody tr').remove();
            setRowsAndButtons();
        }
        await timer(1000)
    }
}


async function setRowsAndButtons() {
    await sendRequest
    let row = ['id', 'login', 'firstName', 'lastName', 'password', 'roles']
    let rowLength = row.length
    let usersLength = users.length

    for (let j = 0; j < usersLength; j++) {
        $('#usersTable > tbody:last-child').append(`<tr id="tr${j}"></tr>`);
        for (let i = 0; i < rowLength; i++) {
            $('#usersTable > tbody tr:last-child').append(`<td id="td${i}">${users[j][row[i]]}</td>`)
        }
        $('#usersTable > tbody tr:last-child')
            .append(`<td><button onclick="test(this)" id="edit${j}" type="button" class="btn btn-primary editColor" data-toggle="modal" href="#tm">Edit</button></td>`)
        buttonsIdsEdit.push('edit' + j)
    }
}

async function test(button) {
    await sendRequest;
    let buttonId = button
    let user = await users[await getIndex(buttonId)];
    document.querySelector('#id').value =  user['id'];
    document.querySelector('#login').value =  user['login'];
    document.querySelector('#FirstName').value = user['firstName'];
    document.querySelector('#LastName').value =  user['lastName'];
    document.querySelector('#password').value = user['password'];
}

async function getModal() {
    let roles = []
    let role
    if (document.querySelector('input[name="r1"]:checked').value !== '' && document.querySelector('input[name="r1"]:checked').value !== null) {
        role =  document.querySelector('input[name="r1"]:checked').value;
    }

    if (role != null) {
        roles.push(role)
    }
    let user = new User()
    user.id = document.querySelector('#id').value
    user.login = document.querySelector('#login').value
    user.firstName = document.querySelector('#FirstName').value
    user.lastName = document.querySelector('#LastName').value
    user.password = document.querySelector('#password').value
    user.roles = roles
    let us2 = await fetch('/users', {
        method: "PATCH",
        body: JSON.stringify(user),
        headers: {"Content-Type": "application/json; charset=utf8"}
    });
    let us3 = await us2.json()

    let userLength = users.length
    let userResp = new User()
    userResp.id = us3.id
    userResp.login = us3.login
    userResp.firstName = us3.firstName
    userResp.lastName = us3.lastName;
    userResp.password = us3.password
    userResp.roles = await us3.roles
    for (let i = 0; i < userLength; i++) {
        if (users[i].id === us3.id) {
            users[i] = userResp
        }
    }
    $('#usersTable > tbody tr').remove();
    await setRowsAndButtons()
    $('#tm').modal('hide');
}




async function getIndex(buttonId) {
    let length = buttonsIdsEdit.length
    let bId = await buttonId.id
    console.log(buttonsIdsEdit[0])
    for (let i = 0; i < length; i++) {
        if (bId === buttonsIdsEdit[i]) {
            return i
        }
    }
    return 'error'
}