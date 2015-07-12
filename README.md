# QL Web Services

A simple Google App Engine app that signs requests for Amazon's ECS API. (The
E-Commerce Service API not the Elastic Container Service API.)

The ECS API used to not requre a secret key so we used to use it directly. Then
one fateful day AWS decided that this API should use signed requests with 
secret keys. This signs requests for ItemSearch requests and ignores everything
else.

## Building

Download a version of Apache Commons Codec and place it into war/WEB-INF/lib.

Use the App Engine tools for Eclipse to add the remaining App Engine SDK jars.

Right-click the project and select Google>Deploy to App Engine.

## License

SignedRequestsHelper.java is Copyright 2009 Amazon.com Inc. Distributed under
the Apache License, Version 2.0

Unless otherwise noted in the source file, all other files in this project are
Copyright 2009 Eduardo Gonzalez. Licensed under the Apache License, Version 2.0.

See LICENSE for details.

