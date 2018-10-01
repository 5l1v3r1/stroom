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

import * as React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { splitAt } from "ramda";
import { compose, withProps } from "recompose";

import ReactTable, { RowInfo, Column } from "react-table";
import "react-table/react-table.css";

import Tooltip from "../../../../components/Tooltip";

interface Location {
  streamNo: number;
  lineNo: number;
  colNo: number;
}
interface ErrorData {
  elementId: string;
  location: Location;
  message: string;
  severity: number;
}

interface TableData {
  elementId: string;
  stream: string;
  line: number;
  col: number;
  message: string;
  severity: number;
}

export interface Props {
  errors: ErrorData[];
}

interface WithProps {
  tableColumns: Column[];
  tableData: TableData[];
  metaAndErrors: any[];
}

interface EnhancedProps extends Props, WithProps {}

const enhance = compose<EnhancedProps, Props>(
  withProps(({ errors }) => ({
    tableColumns: [
      {
        Header: "",
        accessor: "severity",
        Cell: (row: RowInfo) => {
          const location = (
            <React.Fragment>
              <p>Stream: {row.original.stream}</p>
              <p>Line: {row.original.line}</p>
              <p>Column: {row.original.col}</p>
            </React.Fragment>
          );

          //TODO TS upgrade: row.value => row.rowValues. Is this really a RowInfo?
          if (row.rowValues === "INFO") {
            return (
              <Tooltip
                trigger={<FontAwesomeIcon color="blue" icon="info-circle" />}
                content={location}
              />
            );
          } else if (row.rowValues === "WARNING") {
            return (
              <Tooltip
                trigger={
                  <FontAwesomeIcon color="orange" icon="exclamation-circle" />
                }
                content={location}
              />
            );
          } else if (row.rowValues === "ERROR") {
            return (
              <Tooltip
                trigger={
                  <FontAwesomeIcon color="red" icon="exclamation-circle" />
                }
                content={location}
              />
            );
          } else if (row.rowValues === "FATAL") {
            return (
              <Tooltip
                trigger={<FontAwesomeIcon color="red" icon="bomb" />}
                content={location}
              />
            );
          } else {
            return undefined;
          }
        },
        width: 35
      },
      {
        Header: "Element",
        accessor: "elementId",
        maxWidth: 120
      },
      {
        Header: "Message",
        accessor: "message"
      }
    ],
    metaAndErrors: splitAt(1, errors)
  })),
  withProps(({ metaAndErrors }) => ({
    tableData: metaAndErrors[1].map((error: ErrorData) => ({
      elementId: error.elementId,
      stream: error.location.streamNo,
      line: error.location.lineNo,
      col: error.location.colNo,
      message: error.message,
      severity: error.severity
    }))
  }))
);

const ErrorTable = ({
  tableColumns,
  tableData,
  errors,
  metaAndErrors
}: EnhancedProps) => (
  <div className="ErrorTable__container">
    <div className="ErrorTable__reactTable__container">
      <ReactTable
        sortable={false}
        showPagination={false}
        className="ErrorTable__reactTable"
        data={tableData}
        columns={tableColumns}
      />
    </div>
  </div>
);

export default enhance(ErrorTable);
