/**********************************************************************************************
 * Copyright 2009 Eduardo Gonzalez. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 *
 * ********************************************************************************************/

package net.sacredchao.quodlibet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AmazonMirrorServlet extends HttpServlet {
    private static final String AWS_ACCESS_KEY_ID = "ACCESS_KEY_HERE";
    private static final String AWS_SECRET_KEY = "SECRET_KEY_HERE";

    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     *
     *      US: ecs.amazonaws.com
     *      CA: ecs.amazonaws.ca
     *      UK: ecs.amazonaws.co.uk
     *      DE: ecs.amazonaws.de
     *      FR: ecs.amazonaws.fr
     *      JP: ecs.amazonaws.jp
     */
    private static final String ENDPOINT = "ecs.amazonaws.com";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getQueryString() == null) {
            resp.sendRedirect("/");
        }

        // Validate that the string only performs the ItemSearch operation
        int op_index = req.getQueryString().indexOf("Operation=ItemSearch");

        // Couldn't find ItemSearch or there's more than one Operation.  Silently ignore the request.
        if (op_index == -1) {
            resp.sendRedirect("/");
        } else if (req.getQueryString().substring(op_index).indexOf("Operation=") < 0) {
            resp.sendRedirect("/");
        }

        try {
            SignedRequestsHelper helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);

            resp.setContentType("text/xml");
            PrintWriter out = resp.getWriter();

            String requestUrl = helper.sign(req.getQueryString());

            URL ecs = new URL(requestUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ecs.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/");
        }
    }
}
