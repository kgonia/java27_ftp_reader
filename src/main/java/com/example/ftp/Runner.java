package com.example.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class Runner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Runner.class);

    private final RemoteFileTemplate remoteFileTemplate;

    public Runner(RemoteFileTemplate remoteFileTemplate) {
        this.remoteFileTemplate = remoteFileTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        printInPath("");
    }

    public void printInPath(String path){
        Object[] files = remoteFileTemplate.list(path);
        Stream.of(files)
                .forEach(ftpFile -> print(path, (FTPFile)ftpFile));

    }

    public void print(String pathPrefix, FTPFile ftpFile){
        if(ftpFile.isDirectory()){
            printInPath(pathPrefix + "/" + ftpFile.getName());
        } else {
            log.info(pathPrefix  + "/" + ftpFile.getName());
        }
    }
}
