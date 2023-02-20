const SERVER_DATA = {
    SERVER_URL: 'http://localhost:8080',
}

async function getAllUsers()    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users`, {
    method: 'GET',
})

let result = await response.json();
    console.log(result)
}

getAllUsers()