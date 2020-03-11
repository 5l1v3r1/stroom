/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.core.db.migration._V07_00_00.util.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Our own factory class to return the desired SAXParserFactory implementation. This is due to the many xerces
 * implementations available, e.g. the internal one in the JDK, and the one bundled in gwt-dev. This class reports the
 * value of the javax.xml.parsers.SAXParserFactory property which is used by SAXParserFactory to determine which
 * implementation to return. If the property is not set then we hard code it to the xerces implementation in the JRE.
 */
public final class _V07_00_00_SAXParserFactoryFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(_V07_00_00_SAXParserFactoryFactory.class);

    static final String SECURE_PROCESSING_PROPERTY = "stroom.pipeline.parser.secureProcessing";
    private static final String DEFAULT_SAX_PARSER_FACTORY = "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl";
    private static final String IMP_USED = "The SAX Parser factory implementation being used is: ";
    private static final String END = "\".";
    private static final String SYSPROP_SAX_PARSER_FACTORY = "javax.xml.parsers.SAXParserFactory";
    private static final String SYSPROP_SET_TO = "System property \"" + SYSPROP_SAX_PARSER_FACTORY + "\" set to \"";
    private static final String SYSPROP_NOT_SET = "System property \"" + SYSPROP_SAX_PARSER_FACTORY + "\" not set.";

    static {
        try {
            final String factoryName = System.getProperty(SYSPROP_SAX_PARSER_FACTORY);
            if (factoryName == null) {
                LOGGER.info(SYSPROP_NOT_SET);

                System.setProperty(SYSPROP_SAX_PARSER_FACTORY, DEFAULT_SAX_PARSER_FACTORY);
            } else {
                LOGGER.info(SYSPROP_SET_TO + factoryName + END);
            }

            final SAXParserFactory factory = newInstance();
            LOGGER.info(IMP_USED + factory.getClass().getName());
        } catch (final RuntimeException e) {
            LOGGER.error("Unable to configure SAXParserFactory!", e);
        }
    }

    private _V07_00_00_SAXParserFactoryFactory() {
        // Utility class.
    }

    public static SAXParserFactory newInstance() {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        secureProcessing(factory);
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        return factory;
    }

    private static void secureProcessing(final SAXParserFactory factory) {
        try {
            final boolean secureProcessing = _V07_00_00_ParserConfig.secureProcessing;
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, secureProcessing);


//            System.setProperty(XalanConstants.SP_TOTAL_ENTITY_SIZE_LIMIT, "50000010");
//
//            //
//            // Implementation limits: corresponding System Properties of the above
//            // API properties
//            //
//            /**
//             * JDK entity expansion limit; Note that the existing system property
//             * "entityExpansionLimit" with no prefix is still observed
//             */
//            public static final String SP_ENTITY_EXPANSION_LIMIT = "jdk.xml.entityExpansionLimit";
//
//            /**
//             * JDK element attribute limit; Note that the existing system property
//             * "elementAttributeLimit" with no prefix is still observed
//             */
//            public static final String SP_ELEMENT_ATTRIBUTE_LIMIT =  "jdk.xml.elementAttributeLimit";
//
//            /**
//             * JDK maxOccur limit; Note that the existing system property
//             * "maxOccurLimit" with no prefix is still observed
//             */
//            public static final String SP_MAX_OCCUR_LIMIT = "jdk.xml.maxOccurLimit";
//
//            /**
//             * JDK total entity size limit
//             */
//            public static final String SP_TOTAL_ENTITY_SIZE_LIMIT = "jdk.xml.totalEntitySizeLimit";
//
//            /**
//             * JDK maximum general entity size limit
//             */
//            public static final String SP_GENERAL_ENTITY_SIZE_LIMIT = "jdk.xml.maxGeneralEntitySizeLimit";
//
//            /**
//             * JDK node count limit in entities that limits the total number of nodes
//             * in all of entity references.
//             */
//            public static final String SP_ENTITY_REPLACEMENT_LIMIT = "jdk.xml.entityReplacementLimit";
//
//            /**
//             * JDK maximum parameter entity size limit
//             */
//            public static final String SP_PARAMETER_ENTITY_SIZE_LIMIT = "jdk.xml.maxParameterEntitySizeLimit";
//            /**
//             * JDK maximum XML name limit
//             */
//            public static final String SP_XML_NAME_LIMIT = "jdk.xml.maxXMLNameLimit";
//
//            /**
//             * JDK maxElementDepth limit
//             */
//            public static final String SP_MAX_ELEMENT_DEPTH = "jdk.xml.maxElementDepth";


        } catch (final ParserConfigurationException | SAXNotRecognizedException | SAXNotSupportedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
