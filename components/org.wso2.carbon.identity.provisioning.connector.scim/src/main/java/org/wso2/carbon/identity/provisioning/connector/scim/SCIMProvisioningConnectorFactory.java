/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.provisioning.connector.scim;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.common.model.Property;
import org.wso2.carbon.identity.provisioning.AbstractProvisioningConnectorFactory;
import org.wso2.carbon.identity.provisioning.IdentityProvisioningException;
import org.wso2.carbon.identity.provisioning.connector.scim.scim1.SCIMProvisioningConnector;
import org.wso2.carbon.identity.provisioning.connector.scim.scim2.SCIM2ProvisioningConnector;

/**
 * @author
 */
public class SCIMProvisioningConnectorFactory extends AbstractProvisioningConnectorFactory {

    public static final String SCIM = "scim";
    private static final String SCIM_VERSION1 = "scim1";
    private static final String SCIM_VERSION2 = "scim2";
    private static final Log log = LogFactory.getLog(SCIMProvisioningConnectorFactory.class);

    @Override
    protected AbstractSCIMOutboundProvisioningConnector buildConnector(Property[] provisioningProperties)
            throws IdentityProvisioningException {

        String scimVersion = SCIM_VERSION1;
        for (Property property : provisioningProperties) {
            if (SCIMProvisioningConnectorConstants.SCIM_VERSION.equals(property.getName())) {
                scimVersion = property.getValue();
            }
        }

        AbstractSCIMOutboundProvisioningConnector scimProvisioningConnector;

        if(scimVersion.equalsIgnoreCase(SCIM_VERSION1)) {
            scimProvisioningConnector = new SCIMProvisioningConnector();
        } else {
            scimProvisioningConnector = new SCIM2ProvisioningConnector();
        }

        scimProvisioningConnector.init(provisioningProperties);

        if (log.isDebugEnabled()) {
            log.debug("Created new connector of type : " + scimVersion);
        }

        return scimProvisioningConnector;

    }

    @Override
    public String getConnectorType() {
        return SCIM;
    }

}
