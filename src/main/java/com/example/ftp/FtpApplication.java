package com.example.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.filters.FtpSimplePatternFileListFilter;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizer;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizingMessageSource;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.io.File;

@SpringBootApplication
public class FtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtpApplication.class, args);
    }

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("localhost");
        sf.setPort(21);
        sf.setUsername("user");
        sf.setPassword("secret");
        return new CachingSessionFactory<FTPFile>(sf);
    }

    @Bean
    public RemoteFileTemplate remoteFileTemplate() {
        return new RemoteFileTemplate(ftpSessionFactory());
    }

//    @Bean
//    public FtpInboundFileSynchronizer ftpInboundFileSynchronizer() {
//        FtpInboundFileSynchronizer fileSynchronizer = new FtpInboundFileSynchronizer(ftpSessionFactory());
//        fileSynchronizer.setDeleteRemoteFiles(false);
//        fileSynchronizer.setRemoteDirectory("foo");
//        fileSynchronizer.setFilter(new FtpSimplePatternFileListFilter("*.xml"));
//        return fileSynchronizer;
//    }
//
//    @Bean
//    @InboundChannelAdapter(channel = "ftpChannel", poller = @Poller(fixedDelay = "5000"))
//    public MessageSource<File> ftpMessageSource() {
//        FtpInboundFileSynchronizingMessageSource source =
//                new FtpInboundFileSynchronizingMessageSource(ftpInboundFileSynchronizer());
//        source.setLocalDirectory(new File("ftp-inbound"));
//        source.setAutoCreateLocalDirectory(true);
//        source.setLocalFilter(new AcceptOnceFileListFilter<File>());
//        source.setMaxFetchSize(1);
//        return source;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "ftpChannel")
//    public MessageHandler handler() {
//        return new MessageHandler() {
//
//            @Override
//            public void handleMessage(Message<?> message) throws MessagingException {
//                System.out.println(message.getPayload());
//            }
//
//        };
//    }
}
