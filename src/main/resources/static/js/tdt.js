let table = document.querySelector('#tb')
async function getDate() {

    let row = ['login', 'firstName', 'lastName', 'password', 'roles']
    let url = '/users'
    let response = await fetch(url)
    let js = await response.json()

    for (let j = 0; j < js.length; j++) {
        //table.append(tr)
        table.insertAdjacentHTML('afterend',`<tr id="tr${j}">`);
        for (let i = 0; i < row.length; i++) {
          //  $('#tb > tr:last-child').append(`<td id="${i}">${js[j][row[i]]}</td>`);
          //  r += `<td id="${i}">${js[j][row[i]]}</td>`
           table.insertAdjacentHTML('afterend',`<td id="${i}">${js[j][row[i]]}`);
         }
        table.insertAdjacentHTML('afterend','</tr>');
    }
}

getDate()