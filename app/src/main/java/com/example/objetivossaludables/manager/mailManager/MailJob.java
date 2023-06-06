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

    private final String ADMIN = "diego.jimenezadm@gmail.com";
    private final String ADMIN_PWD = "srxidszyqxaeyhnt";
    private Session session;

    public MailJob(String emailCliente, int otp) {
        //DE MOMENTO ES EL ADMIN QUIEN LO RECIBE, HAY QUE PONER QUE COJA EL EMAIL DE LA PREFERENCE
        // O DE OTRO LADO PARA QUE SE LO ENVÍE AL CLIENTE
        buildMail().enviarMail(ADMIN,getContentEmail(otp));
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

    private String getContentEmail(int otp){//plantilla HTML para el mail
        return "<html>\n" +
                "<head>\n" +
                "    <title>Recuperación de contraseña</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"background-color: #f5f5f5; padding: 20px;\">\n" +
                "        <div style=\"background-color: #ffffff; padding: 20px; border-radius: 5px;\">\n" +
                "            <h2 style=\"text-align: center;\">Recuperación de contraseña</h2>\n" +
                "            <p>¡Hola!</p>\n" +
                "            <p>Hemos recibido una solicitud para recuperar tu contraseña. Utiliza el siguiente código de recuperación de 4 dígitos:</p>\n" +
                "            <h1 style=\"text-align: center; font-size: 36px; padding: 20px; background-color: #e9e9e9; border-radius: 5px;\">"+otp+"</h1>\n" +
                "            <p>Utiliza este código para restablecer tu contraseña. Si no has solicitado la recuperación de contraseña, puedes ignorar este mensaje.</p>\n" +
                "            <p>Saludos,<br>Tu aplicación</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}