/*
 * Copyright 2019 Adobe
 * All Rights Reserved.
 *
 * NOTICE: Adobe permits you to use, modify, and distribute this file in
 * accordance with the terms of the Adobe license agreement accompanying
 * it. If you have received this file from a source other than Adobe,
 * then your use, modification, or distribution of it requires the prior
 * written permission of Adobe.
 */

package com.adobe.pdfservices.operation.samples.createpdf;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.exception.SdkException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.CreatePDFOperation;

/**
 * This sample illustrates how to create a PDF file from a DOCX input stream.
 * <p>
 * Refer to README.md for instructions on how to run the samples.
 */
public class CreatePDFFromDOCXInputStream {

    // Initialize the logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePDFFromDOCXInputStream.class);

    public static void main(String[] args) {

        // Prepare an input stream for the file that needs to be converted.
        try (InputStream inputStream = getDOCXInputStream()) {

            // Initial setup, create credentials instance.
            Credentials credentials = Credentials.serviceAccountCredentialsBuilder()
                    .fromFile("pdfservices-api-credentials.json")
                    .build();
            //Create an ExecutionContext using credentials and create a new operation instance.
            ExecutionContext executionContext = ExecutionContext.create(credentials);
            CreatePDFOperation createPdfOperation = CreatePDFOperation.createNew();

            // Set operation input from the source stream by specifying the stream media type.
            FileRef source = FileRef.createFromStream(inputStream, CreatePDFOperation.SupportedSourceFormat.DOCX.getMediaType());
            createPdfOperation.setInput(source);

            // Execute the operation.
            FileRef result = createPdfOperation.execute(executionContext);

            // Save the result to the specified location.
            result.saveAs("output/createPDFFromDOCXStream.pdf");

        } catch (ServiceApiException | IOException | SdkException | ServiceUsageException ex) {
            LOGGER.error("Exception encountered while executing operation", ex);
        }
    }

    private static InputStream getDOCXInputStream() {
        return CreatePDFFromDOCXInputStream.class.getClassLoader().getResourceAsStream("createPDFInput.docx");
    }
}
