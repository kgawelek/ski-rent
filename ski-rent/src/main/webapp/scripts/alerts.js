const urlParams = new URLSearchParams(window.location.search);
urlParams.forEach((value, key) => console.debug("param:", key, " -> ", value));

if(window.location.pathname === '/' && document.referrer === window.location.protocol + '//' + window.location.host + '/login.jsp')
    createAlert(`
        <div class="alert success">
            <span class="closebtn">&times;</span>
            <strong>Pomyślnie zalogowano</strong>
        </div>`);
else if (urlParams.get('login') === 'error') {
    createAlert(`
        <div class="alert warning">
            <span class="closebtn">&times;</span>
            <strong>Nieprawidłowe dane logowania!</strong>
        </div>`);
}
else if (urlParams.get('login') === 'prompt') {
    createAlert(`
        <div class="alert info">
            <span class="closebtn">&times;</span>
            <strong>Zarejestrowano pomyślnie. Możesz teraz się zalogować</strong>
        </div>`);
}
else if (urlParams.get('logout') === 'success') {
    createAlert(`
        <div class="alert success">
            <span class="closebtn">&times;</span>
            <strong>Wylogowano pomyślnie</strong>
        </div>`);
}
else if (urlParams.get('registration') === 'failure') {
    if (urlParams.get('reason') === 'invalidName') {
        createAlert(`
            <div class="alert">
                <span class="closebtn">&times;</span>
                <strong>Wprowadź poprawne imię</strong>
            </div>`);
    }
    else if (urlParams.get('reason') === 'invalidSurname') {
        createAlert(`
            <div class="alert">
                <span class="closebtn">&times;</span>
                <strong>Wprowadź poprawne nazwisko</strong>
            </div>`);
    }
    else if (urlParams.get('reason') === 'invalidDate') {
        createAlert(`
            <div class="alert">
                <span class="closebtn">&times;</span>
                <strong>Wprowadź poprawną datę</strong>
            </div>`);
    }
    else if (urlParams.get('reason') === 'invalidEmail') {
        createAlert(`
            <div class="alert">
                <span class="closebtn">&times;</span>
                <strong>Wprowadź poprawny adres email</strong>
            </div>`);
    }
    else if (urlParams.get('reason') === 'emailTaken') {
        createAlert(`
            <div class="alert">
                <span class="closebtn">&times;</span>
                <strong>Konto o podanym adresie email już istnieje</strong>
            </div>`);
    }
    else if (urlParams.get('reason') === 'invalidPassword') {
        createAlert(`
            <div class="alert">
                <span class="closebtn">&times;</span>
                <strong>Wprowadź poprawne hasło</strong>
            </div>`);
    }
}



// bind close button(s):
const close = document.getElementsByClassName("closebtn");

for (let i = 0; i < close.length; i++) {
    close[i].onclick = function(){
        var div = this.parentElement;
        div.style.opacity = "0";
        setTimeout(function(){ div.style.display = "none"; }, 600);
    }
}


function createAlert(innerHTML, querySelector = '#gorna_czesc_zawartosci') {
    const container = document.querySelector(querySelector);
    const alert = document.createElement("div");
    container.appendChild(alert);
    alert.innerHTML = innerHTML;
}