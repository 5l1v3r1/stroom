import React from 'react';
import { compose } from 'recompose';
import { connect } from 'react-redux';

import ActionBarItem from 'sections/AppChrome/ActionBarItem';

const enhance = compose(connect((state, props) => ({}), {}));

const CreateChildPipeline = props => (
  <ActionBarItem
    buttonProps={{ icon: 'eyedropper' }}
    content="Create a child pipeline, using this one as a parent"
    onClick={() => console.log('Choose folder for child pipeline?')}
  />
);

export default enhance(CreateChildPipeline);