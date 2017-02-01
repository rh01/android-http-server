/**************************************************
 * Android Web Server
 * Based on JavaLittleWebServer (2008)
 * <p/>
 * Copyright (c) Piotr Polak 2016-2016
 **************************************************/

package ro.polak.http.protocol.parser.impl;

import ro.polak.http.RequestStatus;
import ro.polak.http.protocol.parser.MalformedInputException;
import ro.polak.http.protocol.parser.Parser;

/**
 * Utility for parsing HTTP status line.
 *
 * @author Piotr Polak piotr [at] polak [dot] ro
 * @since 201611
 */
public class RequestStatusParser implements Parser<RequestStatus> {

    private static final int NUMBER_OF_CHUNKS = 3;

    /**
     * Parses status line.
     *
     * @param input
     * @return
     * @throws MalformedInputException
     */
    @Override
    public RequestStatus parse(String input) throws MalformedInputException {

        RequestStatus status = new RequestStatus();
        status.setQueryString("");
        String uri;


        String statusArray[] = input.split(" ", NUMBER_OF_CHUNKS);

        if (statusArray.length < NUMBER_OF_CHUNKS) {
            throw new MalformedInputException("Input status string should be composed out of " + NUMBER_OF_CHUNKS + " chunks");
        }

        // First element of the array is the HTTP method
        status.setMethod(statusArray[0].toUpperCase());
        // Second element of the array is the HTTP queryString
        uri = statusArray[1];

        // Protocol is the thrid part of the status line
        if (statusArray.length > 2) {
            status.setProtocol(statusArray[2].trim());
        }

        int questionMarkPosition = uri.indexOf("?");
        if (questionMarkPosition > -1) {
            status.setQueryString(uri.substring(questionMarkPosition + 1));
            uri = uri.substring(0, questionMarkPosition);
        }
        status.setUri(uri);
        return status;
    }
}
