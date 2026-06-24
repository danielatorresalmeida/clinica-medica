document.addEventListener("DOMContentLoaded", function () {
    const tipoUtilizador = document.getElementById("tipoUtilizador");
    const campoEspecialidade = document.getElementById("campoEspecialidade");
    const inputEspecialidade = document.querySelector("input[name='especialidade']");
    const formularios = document.querySelectorAll("form");

    if (tipoUtilizador && campoEspecialidade) {
        mostrarCampoEspecialidade();

        tipoUtilizador.addEventListener("change", function () {
            mostrarCampoEspecialidade();
        });
    }

    formularios.forEach(function (formulario) {
        formulario.addEventListener("submit", function () {
            alert("Registo submetido com sucesso!");
        });
    });

    function mostrarCampoEspecialidade() {
        if (tipoUtilizador.value === "MEDICO") {
            campoEspecialidade.classList.remove("escondida");

            if (inputEspecialidade) {
                inputEspecialidade.required = true;
            }
        } else {
            campoEspecialidade.classList.add("escondida");

            if (inputEspecialidade) {
                inputEspecialidade.required = false;
                inputEspecialidade.value = "";
            }
        }
    }
});

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

function destacarNomes() {
    const nomes = document.querySelectorAll(".nome-registo");

    nomes.forEach(function (nome) {
        nome.classList.toggle("nome-destacado");
    });
}