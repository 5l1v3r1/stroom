package stroom.core.db.migration._V07_00_00.explorer;

import stroom.core.db.migration._V07_00_00.docref._V07_00_00_DocRef;
import stroom.docref.DocRef;

public final class _V07_00_00_ExplorerConstants {
    public static final String SYSTEM = "System";
    public static final String FOLDER = "Folder";
    public static final _V07_00_00_DocRef ROOT_DOC_REF = new _V07_00_00_DocRef(SYSTEM, "0",SYSTEM);

    private _V07_00_00_ExplorerConstants() {
    }
}
