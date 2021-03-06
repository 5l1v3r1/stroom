package stroom.authentication.impl.db;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;
import stroom.auth.db.Tables;
import stroom.auth.db.tables.records.JsonWebKeyRecord;
import stroom.authentication.dao.JwkDao;
import stroom.db.util.JooqUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class JwkDaoImpl implements JwkDao {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JwkDaoImpl.class);

    private AuthDbConnProvider authDbConnProvider;

    @Inject
    JwkDaoImpl(final AuthDbConnProvider authDbConnProvider) {
        this.authDbConnProvider = authDbConnProvider;
    }

    /**
     * This will always return a single public key. If the key doesn't exist it will create it.
     * If it does exist it will return that.
     */
    @Override
    public PublicJsonWebKey readJwk() {
        try {
            JooqUtil.context(authDbConnProvider, context ->
                    context.selectFrom(Tables.JSON_WEB_KEY).fetchOne());
            JsonWebKeyRecord existingJwkRecord = JooqUtil.contextResult(authDbConnProvider, context -> context
                    .selectFrom(Tables.JSON_WEB_KEY).fetchOne());

            if (existingJwkRecord == null) {
                LOGGER.info("We don't have a saved JWK so we'll create one and save it for use next time.");
                // We need to set up the jwkId so we know which JWTs were signed by which JWKs.
                String jwkId = UUID.randomUUID().toString();
                RsaJsonWebKey jwk = RsaJwkGenerator.generateJwk(2048);
                jwk.setKeyId(jwkId);

                // Persist the public key
                JsonWebKeyRecord jwkRecord = new JsonWebKeyRecord();
                jwkRecord.setKeyid(jwkId);
                jwkRecord.setJson(jwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
                JooqUtil.context(authDbConnProvider, context -> context.executeInsert(jwkRecord));

                return jwk;
            } else {
                LOGGER.info("We do have a saved JWK so we'll re-use it.");
                PublicJsonWebKey jwk = RsaJsonWebKey.Factory.newPublicJwk(existingJwkRecord.getJson());
                return jwk;
            }
        } catch (JoseException e) {
            LOGGER.error("Unable to create JWK!", e);
            throw new RuntimeException(e);
        }
    }
}
