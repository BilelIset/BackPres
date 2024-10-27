package tn.isetsf.presence.webThymeleaf;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class FtpService {

    private final String server = "51.77.210.237"; // Remplacez par l'adresse IP de votre VPS
    private final int port = 21; // Port par défaut pour FTP
    private final String user = "spring"; // Nom d'utilisateur FTP
    private final String pass = "123456"; // Mot de passe FTP

    public void uploadFile(String localFilePath, String remoteFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.enterLocalPassiveMode();

        ftpClient.login(user, pass);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);



    }

    public void downloadFile(String remoteFilePath, String localFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("51.77.210.237", 21); // Remplacez par l'adresse IP correcte de votre VPS
        ftpClient.enterLocalPassiveMode();

        ftpClient.login(user, pass);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        try (FileOutputStream output = new FileOutputStream(localFilePath)) {
            ftpClient.retrieveFile(remoteFilePath, output);
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
