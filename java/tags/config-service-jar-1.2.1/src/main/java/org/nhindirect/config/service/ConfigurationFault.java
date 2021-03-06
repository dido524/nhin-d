/* 
Copyright (c) 2010, NHIN Direct Project
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer 
   in the documentation and/or other materials provided with the distribution.  
3. Neither the name of the The NHIN Direct Project (nhindirect.org) nor the names of its contributors may be used to endorse or promote 
   products derived from this software without specific prior written permission.
   
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS 
BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.nhindirect.config.service;

import javax.xml.ws.WebFault;

/**
 * Configuration fault class.
 */
@WebFault()
public class ConfigurationFault {

    private String message = "";

    private ConfigurationError error = ConfigurationError.Unknown;

    /**
     * Default constructor.
     */
    public ConfigurationFault() {
    }

    /**
     * Construct a new ConfigurationFault with a specific ConfigurationError.
     * 
     * @param anError
     *            A ConfigurationError.
     */
    public ConfigurationFault(ConfigurationError anError) {
        setError(anError);
    }

    /**
     * Construct a new ConfigurationFault with a specific message and
     * ConfigurationError.
     * 
     * @param aMsg
     *            A message.
     * @param anError
     *            A ConfigurationError.
     */
    public ConfigurationFault(String aMsg, ConfigurationError anError) {
        setMessage(aMsg);
        setError(anError);
    }

    /**
     * Return the value of message.
     * 
     * @return the value of message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message.
     * 
     * @param message
     *            The value of message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the value of error.
     * 
     * @return the value of error.
     */
    public ConfigurationError getError() {
        return error;
    }

    /**
     * Set the value of error.
     * 
     * @param error
     *            The value of error.
     */
    public void setError(ConfigurationError error) {
        this.error = error;
    }

    /**
     * Convert an Exception to a ConfigurationFault.
     * 
     * TODO Map exceptions to fault errors
     * 
     * @param e The Exception.
     * @return
     */
    public static ConfigurationFault errorToFault(Exception e) {
        ConfigurationFault result = new ConfigurationFault(e.getMessage(), ConfigurationError.Unknown);

        return result;
    }
}
