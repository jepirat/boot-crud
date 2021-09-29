
let users
async function test () {
    let users = []
    let t = await fetch('/users')
    let j = await t.json()
    for (let i = 0; i < j.length; i++) {
        users.push(j[i])
    }
}

test()
async function test1 () {
     console.log(users)
}
test1()



