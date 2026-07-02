package com.iefp.clinicaMedica.config;

import com.iefp.clinicaMedica.model.Utilizador;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String caminho = request.getRequestURI();
        String metodo = request.getMethod();

        // Páginas públicas
        if (caminho.equals("/")
                || caminho.equals("/login")
                || caminho.equals("/acesso-negado")
                || caminho.equals("/error")
                || caminho.startsWith("/css/")
                || caminho.startsWith("/js/")
                || caminho.startsWith("/images/")
                || caminho.startsWith("/h2-console")) {
            return true;
        }

        // Logout permitido
        if (caminho.equals("/logout")) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("utilizadorLogado") == null) {
            response.sendRedirect("/login");
            return false;
        }

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");
        String tipo = utilizador.getTipo();

        // ADMIN pode aceder a tudo, incluindo criar utilizadores
        if (tipo.equals("ADMIN")) {
            return true;
        }

        // SECRETARIA pode gerir a clínica, mas não pode criar utilizadores
        if (tipo.equals("SECRETARIA")) {
            if (podeSecretaria(caminho, metodo)) {
                return true;
            }
        }

        // Permissões do médico
        if (tipo.equals("MEDICO")) {
            if (podeMedico(caminho, metodo)) {
                return true;
            }
        }

        // Permissões do paciente
        if (tipo.equals("PACIENTE")) {
            if (podePaciente(caminho, metodo)) {
                return true;
            }
        }

        response.sendRedirect("/acesso-negado");
        return false;
    }

    // Define o que a secretária pode fazer
    private boolean podeSecretaria(String caminho, String metodo) {

        // A secretária NÃO pode criar utilizadores
        if (caminho.equals("/registar-utilizador")
                || caminho.equals("/registar")
                || caminho.equals("/registo/utilizador")
                || caminho.startsWith("/registo/")) {
            return false;
        }

        // Pode aceder ao resto das páginas da clínica
        return true;
    }

    // Define o que o médico pode fazer
    private boolean podeMedico(String caminho, String metodo) {

        if (metodo.equals("GET")) {
            return caminho.equals("/consultas")
                    || caminho.equals("/exames")
                    || caminho.equals("/receitas")
                    || caminho.equals("/disponibilidades");
        }

        if (metodo.equals("POST")) {
            return caminho.equals("/disponibilidades")
                    || caminho.equals("/exames")
                    || caminho.equals("/receitas");
        }

        return false;
    }

    // Define o que o paciente pode fazer
    private boolean podePaciente(String caminho, String metodo) {

        if (metodo.equals("GET")) {
            return caminho.equals("/consultas")
                    || caminho.equals("/exames")
                    || caminho.equals("/receitas");
        }

        if (metodo.equals("POST")) {
            return caminho.equals("/consultas");
        }

        return false;
    }
}