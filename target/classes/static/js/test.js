const url = '/users';
let use


class Test {
    startModal(button) {
        console.log(button.id)
    }

}
class User {
    id
    login
    firstName
    lastName
    password
    roles = []
    constructor(id, login, firstName, lastName, password, roles) {
        this._id = id;
        this._login = login;
        this._firstName = firstName;
        this._lastName = lastName;
        this._password = password;
        this._roles = roles;
    }


    get login() {
        return this._login;
    }
};

async function sendRequest(url) {
    let users = []
    let row = ['id', 'login', 'firstName', 'lastName', 'password', 'roles']
    let response = await fetch(url)
    let js = await response.json()
    let test = new Test()

    for (let j = 0; j < js.length; j++) {
        let fields = []
        $('#usersTable > tbody:last-child').append(`<tr id="tr${j}"></tr>`);
        for (let i = 0; i < row.length; i++) {
            $('#usersTable > tbody tr:last-child').append(`<td id="${i}">${js[j][row[i]]}</td>`)
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

        $('#usersTable > tbody tr:last-child')
            .append(`<td><button onclick="new Test.startModal(this)" id="${j}" type="button" class="btn btn-primary editColor" data-toggle="modal" href="#tm">Edit</button></td>`)
    }

}



sendRequest(url)

function createUser(id, login, firstName, lastName, password, roles) {
    return  new User(id, login, firstName, lastName, password, roles);
}



// let u = await sendRequest(url).then(r => {return r.json()})
// console.log('users', u)


// const buttons = document.querySelectorAll('button[type="button"]');
// console.log(buttons.length)
// for (let i = 0; i < buttons.length; i++) {
//     const btn = buttons[i];
//     btn.addEventListener('click' , (e) => startModal(i , e.target.value))
// }