/*
 * This file is generated by jOOQ.
 */
package stroom.annotations.impl.db.jooq;


import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

import stroom.annotations.impl.db.jooq.tables.Annotation;
import stroom.annotations.impl.db.jooq.tables.AnnotationHistory;
import stroom.annotations.impl.db.jooq.tables.records.AnnotationHistoryRecord;
import stroom.annotations.impl.db.jooq.tables.records.AnnotationRecord;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>stroom</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AnnotationRecord, Integer> IDENTITY_ANNOTATION = Identities0.IDENTITY_ANNOTATION;
    public static final Identity<AnnotationHistoryRecord, Integer> IDENTITY_ANNOTATION_HISTORY = Identities0.IDENTITY_ANNOTATION_HISTORY;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AnnotationRecord> KEY_ANNOTATION_PRIMARY = UniqueKeys0.KEY_ANNOTATION_PRIMARY;
    public static final UniqueKey<AnnotationRecord> KEY_ANNOTATION_META_ID_EVENT_ID = UniqueKeys0.KEY_ANNOTATION_META_ID_EVENT_ID;
    public static final UniqueKey<AnnotationHistoryRecord> KEY_ANNOTATION_HISTORY_PRIMARY = UniqueKeys0.KEY_ANNOTATION_HISTORY_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AnnotationHistoryRecord, AnnotationRecord> ANNOTATION_HISTORY_FK_ANNOTATION_ID = ForeignKeys0.ANNOTATION_HISTORY_FK_ANNOTATION_ID;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AnnotationRecord, Integer> IDENTITY_ANNOTATION = Internal.createIdentity(Annotation.ANNOTATION, Annotation.ANNOTATION.ID);
        public static Identity<AnnotationHistoryRecord, Integer> IDENTITY_ANNOTATION_HISTORY = Internal.createIdentity(AnnotationHistory.ANNOTATION_HISTORY, AnnotationHistory.ANNOTATION_HISTORY.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AnnotationRecord> KEY_ANNOTATION_PRIMARY = Internal.createUniqueKey(Annotation.ANNOTATION, "KEY_annotation_PRIMARY", Annotation.ANNOTATION.ID);
        public static final UniqueKey<AnnotationRecord> KEY_ANNOTATION_META_ID_EVENT_ID = Internal.createUniqueKey(Annotation.ANNOTATION, "KEY_annotation_meta_id_event_id", Annotation.ANNOTATION.META_ID, Annotation.ANNOTATION.EVENT_ID);
        public static final UniqueKey<AnnotationHistoryRecord> KEY_ANNOTATION_HISTORY_PRIMARY = Internal.createUniqueKey(AnnotationHistory.ANNOTATION_HISTORY, "KEY_annotation_history_PRIMARY", AnnotationHistory.ANNOTATION_HISTORY.ID);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<AnnotationHistoryRecord, AnnotationRecord> ANNOTATION_HISTORY_FK_ANNOTATION_ID = Internal.createForeignKey(stroom.annotations.impl.db.jooq.Keys.KEY_ANNOTATION_PRIMARY, AnnotationHistory.ANNOTATION_HISTORY, "annotation_history_fk_annotation_id", AnnotationHistory.ANNOTATION_HISTORY.FK_ANNOTATION_ID);
    }
}
