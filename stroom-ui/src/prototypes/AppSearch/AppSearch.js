import React from 'react';

import { compose } from 'recompose';
import { connect } from 'react-redux';
import { Button, Header, Icon, Modal, Menu, Input, Breadcrumb } from 'semantic-ui-react';

import { actionCreators as appSearchActionCreators } from './redux';
import withExplorerTree from 'components/DocExplorer/withExplorerTree';

const { appSearchClosed, appSearchTermUpdated } = appSearchActionCreators;

const enhance = compose(
  withExplorerTree,
  connect(
    (state, props) => ({
      isOpen: state.appSearch.isOpen,
      searchTerm: state.appSearch.searchTerm,
      searchResults: state.appSearch.searchResults,
    }),
    { appSearchClosed, appSearchTermUpdated },
  ),
);

const AppSearch = ({
  isOpen,
  searchTerm,
  appSearchClosed,
  appSearchTermUpdated,
  searchResults,
}) => (
  <Modal open={isOpen} onClose={appSearchClosed} size="small" dimmer="inverted">
    <Header icon="search" content="App Search" />
    <Modal.Content>
      <Input
        icon="search"
        placeholder="Search..."
        value={searchTerm}
        onChange={e => appSearchTermUpdated(e.target.value)}
      />
      <Menu vertical fluid>
        {searchResults.map((searchResult, i, arr) => {
          // Compose the data that provides the breadcrumb to this node
          const sections = searchResult.lineage.map(l => ({
            key: l.name,
            content: l.name,
            link: false,
          }));

          return (
            <Menu.Item
              key={i}
              onClick={() => {
                console.log('OPEN DOC REF'); // todo
                appSearchClosed();
              }}
            >
              <div style={{ width: '50rem' }}>
                <Breadcrumb size="mini" icon="right angle" sections={sections} />
                <div className="doc-ref-dropdown__item-name">{searchResult.name}</div>
              </div>
            </Menu.Item>
          );
        })}
      </Menu>
    </Modal.Content>
    <Modal.Actions>
      <Button negative onClick={appSearchClosed} inverted>
        <Icon name="checkmark" /> Close
      </Button>
    </Modal.Actions>
  </Modal>
);

export default enhance(AppSearch);