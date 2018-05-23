/*
 * Copyright 2018 Crown Copyright
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
import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { LineContainer, LineTo } from 'components/LineTo';

import { withPipeline } from './withPipeline';

import PipelineElement from './PipelineElement';

import './PipelineEditor.css';

import { iterateNodes } from 'lib/treeUtils';

class PipelineEditor extends Component {
  static propTypes = {
    pipelineId: PropTypes.string.isRequired,
    pipeline: PropTypes.object.isRequired,
  };

  // the state will hold the layout information, with layout information attached
  state = {
    elementLayouts: {},
  };

  static getDerivedStateFromProps(nextProps, prevState) {
    const elementLayouts = {};

    // Current height of a column, keyed on horizontal position
    const verticalPositionsByHorz = {};
    const currentHeight = 1;

    const HORIZONTAL_SPACING = 150;
    const VERTICAL_SPACING = 100;
    const commonStyle = {
      position: 'absolute',
    };

    iterateNodes(nextProps.pipeline.asTree, (lineage, node) => {
      const horizontalPos = lineage.length;
      let verticalPos = currentHeight;
      if (verticalPositionsByHorz[horizontalPos]) {
        verticalPos = verticalPositionsByHorz[horizontalPos] + 1;
      }
      verticalPositionsByHorz[horizontalPos] = verticalPos;

      elementLayouts[node.id] = {
        horizontalPos,
        verticalPos,
        style: {
          ...commonStyle,
          top: `${verticalPos * VERTICAL_SPACING}px`,
          left: `${horizontalPos * HORIZONTAL_SPACING}px`,
        },
      };
    });

    return {
      elementLayouts,
    };
  }

  renderElements(element) {
    return this.props.pipeline.pipeline.elements.add.element.map(e => (
      <div key={e.id} id={e.id} style={this.state.elementLayouts[e.id].style}>
        <PipelineElement pipelineId={this.props.pipelineId} elementId={e.id} />
      </div>
    ));
  }

  renderLines() {
    return this.props.pipeline.pipeline.links.add.link
      .map(l => ({ ...l, lineId: `${l.from}-${l.to}` }))
      .map(l => <LineTo lineId={l.lineId} key={l.lineId} fromId={l.from} toId={l.to} />);
  }

  render() {
    const { pipelineId, pipeline } = this.props;

    return (
      <div className="Pipeline-editor">
        <LineContainer lineContextId={`pipeline-lines-${pipelineId}`}>
          <h4>Pipeline Editor {pipelineId}</h4>
          {this.renderElements(pipeline.asTree)}
          {this.renderLines()}
        </LineContainer>

        <div>Pipeline Element Settings</div>
      </div>
    );
  }
}

export default withPipeline(PipelineEditor);
