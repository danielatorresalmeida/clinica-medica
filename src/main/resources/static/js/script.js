document.addEventListener("DOMContentLoaded", function () {
    iniciarLogin();
    iniciarTipoUtilizador();
    iniciarTabs();
    iniciarDestacarNomes();
    iniciarMostrarSenha();
});

/* =========================
   LOGIN
========================= */

function iniciarLogin() {
    const body = document.body;
    const loginForm = document.getElementById("loginForm");
    const loginCard = document.getElementById("loginCard");
    const emailInput = document.getElementById("email");
    const senhaInput = document.getElementById("senha");
    const passwordToggle = document.getElementById("passwordToggle");
    const loginError = document.getElementById("loginError");

    if (!loginForm || !emailInput || !senhaInput) {
        return;
    }

    function mostrarErro(mensagem) {
        if (loginError) {
            const textoErro =
                loginError.querySelector(".error-text") ||
                loginError.querySelector("span:last-child");

            if (textoErro && mensagem) {
                textoErro.textContent = mensagem;
            }

            loginError.classList.remove("escondida");
        }

        if (loginCard) {
            loginCard.classList.add("is-error");
        }

        body.dataset.loginError = "true";

        emailInput.classList.add("input-erro");
        senhaInput.classList.add("input-erro");
    }

    function limparErro() {
        if (loginError) {
            loginError.classList.add("escondida");
        }

        if (loginCard) {
            loginCard.classList.remove("is-error");
        }

        body.dataset.loginError = "false";

        emailInput.classList.remove("input-erro");
        senhaInput.classList.remove("input-erro");
    }

    const params = new URLSearchParams(window.location.search);

    if (params.has("erro") || params.has("error")) {
        mostrarErro("Email ou senha inválidos. Verifique os dados e tente novamente.");
    }

    if (passwordToggle) {
        passwordToggle.addEventListener("click", function () {
            alternarVisibilidadeSenha(senhaInput, passwordToggle);
        });
    }

    emailInput.addEventListener("input", limparErro);
    senhaInput.addEventListener("input", limparErro);

    loginForm.addEventListener("submit", function (event) {
        const email = emailInput.value.trim();
        const senha = senhaInput.value.trim();

        if (!email || !senha) {
            event.preventDefault();
            mostrarErro("Preencha o email e a senha antes de entrar.");
            return;
        }

        limparErro();

        /*
           Não usar event.preventDefault() aqui.
           Se os campos estiverem preenchidos, o formulário segue para o Spring Boot.
        */
    });
}

/* =========================
   TIPO DE UTILIZADOR / MÉDICO
========================= */

function iniciarTipoUtilizador() {
    const tipoUtilizador = document.getElementById("tipoUtilizador");
    const campoEspecialidade = document.getElementById("campoEspecialidade");

    /*
       Importante:
       especialidade pode ser input ou select.
       Por isso usamos [name='especialidade'] em vez de input[name='especialidade'].
    */
    const campoEspecialidadeInput = document.querySelector("[name='especialidade']");

    if (!tipoUtilizador || !campoEspecialidade) {
        return;
    }

    function mostrarCampoEspecialidade() {
        if (tipoUtilizador.value === "MEDICO") {
            campoEspecialidade.classList.remove("escondida");

            if (campoEspecialidadeInput) {
                campoEspecialidadeInput.required = true;
            }
        } else {
            campoEspecialidade.classList.add("escondida");

            if (campoEspecialidadeInput) {
                campoEspecialidadeInput.required = false;
                campoEspecialidadeInput.value = "";
            }
        }
    }

    mostrarCampoEspecialidade();

    tipoUtilizador.addEventListener("change", mostrarCampoEspecialidade);
}

/* =========================
   TABS
========================= */

function iniciarTabs() {
    const botoesComDataTab = document.querySelectorAll("[data-tab]");

    if (botoesComDataTab.length === 0) {
        return;
    }

    botoesComDataTab.forEach(function (botao) {
        botao.addEventListener("click", function () {
            mostrarTab(botao.dataset.tab, botao);
        });
    });
}

function mostrarTab(tabId, botaoClicado) {
    const conteudos = document.querySelectorAll(".tab-conteudo");
    const botoes = document.querySelectorAll(".tab-botao");

    conteudos.forEach(function (conteudo) {
        conteudo.classList.add("escondida");
    });

    botoes.forEach(function (botao) {
        botao.classList.remove("tab-ativa");
    });

    const tabSelecionada = document.getElementById(tabId);

    if (tabSelecionada) {
        tabSelecionada.classList.remove("escondida");
    }

    if (botaoClicado) {
        botaoClicado.classList.add("tab-ativa");
    }
}

/* =========================
   DESTACAR NOMES
========================= */

function iniciarDestacarNomes() {
    const botaoDestacar = document.getElementById("botaoDestacarNomes");

    if (!botaoDestacar) {
        return;
    }

    botaoDestacar.addEventListener("click", destacarNomes);
}

function destacarNomes() {
    const nomes = document.querySelectorAll(".nome-registo");

    nomes.forEach(function (nome) {
        nome.classList.toggle("nome-destacado");
    });
}

/* =========================
   MOSTRAR / OCULTAR SENHA
========================= */

function iniciarMostrarSenha() {
    const botoesMostrarSenha = document.querySelectorAll("[data-password-toggle]");

    if (botoesMostrarSenha.length === 0) {
        return;
    }

    botoesMostrarSenha.forEach(function (botao) {
        botao.addEventListener("click", function () {
            const inputId = botao.getAttribute("data-password-toggle");
            let inputSenha = null;

            /*
               Caso 1:
               <button data-password-toggle="senha">
            */
            if (inputId) {
                inputSenha = document.getElementById(inputId);
            }

            /*
               Caso 2:
               <button data-password-toggle>
               Procura o input dentro do mesmo bloco .input-control.
            */
            if (!inputSenha) {
                const grupo = botao.closest(".input-control");
                inputSenha = grupo ? grupo.querySelector("input[type='password'], input[type='text']") : null;
            }

            if (!inputSenha) {
                return;
            }

            alternarVisibilidadeSenha(inputSenha, botao);
        });
    });
}

function alternarVisibilidadeSenha(inputSenha, botao) {
    const senhaEstaOculta = inputSenha.type === "password";

    inputSenha.type = senhaEstaOculta ? "text" : "password";

    botao.setAttribute(
        "aria-label",
        senhaEstaOculta ? "Ocultar senha" : "Mostrar senha"
    );

    botao.setAttribute(
        "aria-pressed",
        String(senhaEstaOculta)
    );

    if (!botao.querySelector("svg")) {
        botao.textContent = senhaEstaOculta ? "Ocultar" : "Mostrar";
    }
}