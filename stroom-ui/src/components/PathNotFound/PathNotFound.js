/*
 * Copyright 2017 Crown Copyright
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

import React from 'react';
import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Grid, Header } from 'semantic-ui-react';

import IconHeader from 'components/IconHeader';

const PathNotFound = ({ message }) => (
  <div className="path-not-found">
    <IconHeader icon='exclamation-triangle' text='Page not found!' />
    <div className="path-not-found__content">
      <p>{message}</p>
    </div>
  </div>
);

PathNotFound.propTypes = {
  message: PropTypes.string.isRequired,
};

PathNotFound.defaultProps = {
  message: "There's nothing here I'm afraid.",
};

export default PathNotFound;
