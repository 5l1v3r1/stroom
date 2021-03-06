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

package stroom.index.shared;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fusesource.restygwt.client.DirectRestService;
import stroom.docref.DocRef;
import stroom.util.shared.ResourcePaths;
import stroom.util.shared.RestResource;
import stroom.util.shared.ResultPage;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Api(value = "index - /v2")
@Path(IndexResource.BASE_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IndexResource extends RestResource, DirectRestService {
    String BASE_PATH = "/index" + ResourcePaths.V2;
    String SHARD_DELETE_SUB_PATH = "/shard/delete";
    //    String SHARD_CLOSE_SUB_PATH = "/shard/close";
    String SHARD_FLUSH_SUB_PATH = "/shard/flush";

    @POST
    @Path("/read")
    @ApiOperation(
            value = "Get an index doc",
            response = IndexDoc.class)
    IndexDoc read(DocRef docRef);

    @PUT
    @Path("/update")
    @ApiOperation(
            value = "Update an index doc",
            response = IndexDoc.class)
    IndexDoc update(IndexDoc indexDoc);

    @POST
    @Path("/shard/find")
    @ApiOperation(
            value = "Find matching index shards",
            response = ResultPage.class)
    ResultPage<IndexShard> findIndexShards(FindIndexShardCriteria criteria);

    @DELETE
    @Path(SHARD_DELETE_SUB_PATH)
    @ApiOperation(
            value = "Delete matching index shards",
            response = Integer.class)
    Long deleteIndexShards(@QueryParam("nodeName") String nodeName, FindIndexShardCriteria criteria);

//    @PUT
//    @Path(SHARD_CLOSE_SUB_PATH)
//    @ApiOperation(
//            value = "Close matching index shards",
//            response = Integer.class)
//    Long closeIndexShards(@QueryParam("nodeName") String nodeName, FindIndexShardCriteria criteria);

    @PUT
    @Path(SHARD_FLUSH_SUB_PATH)
    @ApiOperation(
            value = "Flush matching index shards",
            response = Integer.class)
    Long flushIndexShards(@QueryParam("nodeName") String nodeName, FindIndexShardCriteria criteria);
}