package com.ripplar_games.mesh_io.mesh;

import java.nio.ByteBuffer;

import com.ripplar_games.mesh_io.vertex.VertexFormat;

public class NullMesh implements IMesh {
    private static final ByteBuffer INDICES_BUFFER = BufferUtil.with(new short[0]);
    private static final ByteBuffer VERTICES_BUFFER = BufferUtil.with(new float[0]);

    @Override
    public VertexFormat getVertexFormat() {
        return VertexFormat.EMPTY;
    }

    @Override
    public int getVertexCount() {
        return 0;
    }

    @Override
    public int getFaceCount() {
        return 0;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public ByteBuffer getVertices() {
        return VERTICES_BUFFER;
    }

    @Override
    public ByteBuffer getIndices() {
        return INDICES_BUFFER;
    }
}
