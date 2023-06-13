package com.example.objetivossaludables.manager.mailManager;

import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailJob{

    private final String ADMIN = "dam2susanakatia@gmail.com";
    private final String ADMIN_PWD = "krnmvmehznbicxrm";
    private Session session;

    public MailJob(String emailCliente, int otp) {
        buildMail().enviarMail(emailCliente, getContentEmailOTP(otp));
    }

    public MailJob(String correo, String contenido){
        buildMail().enviarMail(getContentEmailReview(correo,contenido));
    }

    private MailJob buildMail(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session=  Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ADMIN, ADMIN_PWD);//ADMIN SIEMPRE
                    }
                });

        return this;
    }

    private void enviarMail(String recibeMail, String contenido){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ADMIN)); //El usuario ADMIN SIEMPRE
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recibeMail));// El que quiere recuperar la contraseña
            message.setSubject("Recuperación de contraseña");
            message.setContent(contenido, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            Log.d("MailJob", e.getMessage());
        }
    }

    private void enviarMail(String contenido){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ADMIN)); //El usuario ADMIN SIEMPRE
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ADMIN));// El que quiere recuperar la contraseña
            message.setSubject("Comentario sobre la APP");
            message.setContent(contenido, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            Log.d("MailJob", e.getMessage());
        }
    }

    private String getContentEmailOTP(int otp){//plantilla HTML para el mail OTP
        return "<html>\n" +
                "<head>\n" +
                "    <title>Recuperación de contraseña</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"background-color: #f5f5f5; padding: 20px;\">\n" +
                "        <div style=\"background-color: #ffffff; padding: 20px; border-radius: 5px;\">\n" +
                "            <h2 style=\"text-align: center;\">Recuperación de contraseña</h2>\n" +
                "            <p>¡Saludos desde el equipo de atención al cliente!</p>\n" +
                "            <p>Hemos recibido una solicitud para recuperar tu contraseña. Utiliza el siguiente código de recuperación de 4 dígitos:</p>\n" +
                "            <h1 style=\"text-align: center; font-size: 36px; padding: 20px; background-color: #e9e9e9; border-radius: 5px;\">"+otp+"</h1>\n" +
                "            <p>Utiliza este código para restablecer tu contraseña. Si no has solicitado la recuperación de contraseña, puedes ignorar este mensaje.</p>\n" +
                "            <p>Saludos,<br>¡¡El equipo de Objetivos Saludables!!</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    private String getContentEmailReview(String email, String mensaje){//plantilla HTML para enviar un comentario
        return "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f7f7f7; }"
                + ".header { background-color: #4285f4; color: #fff; padding: 20px; text-align: center; }"
                + ".content { padding: 20px; background-color: #fff; }"
                + ".review { padding: 10px; margin-top: 20px; margin-bottom: 20px;background-color: #D1D0D0; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<h1>¡Gracias por tu revisión!</h1>"
                + "</div>"
                + "<div class='content'>"
                + "<p>Estimado equipo de Objetivos saludables,</p>"
                + "<p>Recibido nuevo comentario:</p>"
                + "<div class='review'>"
                + "<p><strong>Email: </strong> " + email + "</p>"
                + "<p><strong>Contenido de la revisión: </strong></p>"
                + "<p>" + mensaje + "</p>"
                + "</div>"
                + "<p>Por favor, tengan en cuenta esta revisión y tomen las acciones necesarias.</p>"
                + "<p>Gracias,</p>"
                + "<p>Tu aplicación de Objetivos saludables</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}