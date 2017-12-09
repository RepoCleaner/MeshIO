package com.ripplar_games.mesh_io.mesh;

import java.nio.ByteBuffer;
import java.util.Random;

import com.ripplar_games.mesh_io.TestUtil;
import com.ripplar_games.mesh_io.index.IndicesDataType;
import com.ripplar_games.mesh_io.vertex.VertexFormat;
import com.ripplar_games.mesh_io.vertex.VertexType;
import org.junit.Assert;
import org.junit.Test;

public class EditableMeshTest {
    private static final Random RANDOM = new Random();

    @Test
    public void testRandom() {
        EditableMesh mesh = new EditableMesh();
        for (int i = 0; i < 25000; i++) {
            int testIndex = RANDOM.nextInt(6);
            switch (testIndex) {
                case 0:
                    testFaceCount(mesh);
                    break;
                case 1:
                    testVertexCount(mesh);
                    break;
                case 2:
                    testMeshAndIndicesTypes(mesh);
                    break;
                case 3:
                    testVertexFormat(mesh);
                    break;
                case 4:
                    testFaceIndices(mesh);
                    break;
                case 5:
                    testVertexData(mesh);
                    break;
                default:
                    Assert.fail();
            }
        }
    }

    private void testFaceCount(EditableMesh mesh) {
        int faces = RANDOM.nextInt(10);
        mesh.setFaceCount(faces);
        Assert.assertEquals(faces, mesh.getFaceCount());
    }

    private void testVertexCount(EditableMesh mesh) {
        int vertices = RANDOM.nextInt(10);
        mesh.setVertexCount(vertices);
        Assert.assertEquals(vertices, mesh.getVertexCount());
    }

    private void testMeshAndIndicesTypes(EditableMesh mesh) {
        MeshType meshType = TestUtil.randomMeshType();
        IndicesDataType<?> indicesDataType = TestUtil.randomIndicesDataType();
        mesh.setMeshType(meshType);
        mesh.setIndicesDataType(indicesDataType);
        ByteBuffer indices = mesh.getIndices();
        int expectedIndicesCount = meshType.getOffsetsLength() * mesh.getFaceCount();
        int actualIndicesCount = indices.capacity() / indicesDataType.bytesPerDatum();
        Assert.assertEquals(expectedIndicesCount, actualIndicesCount);
    }

    private void testVertexFormat(EditableMesh mesh) {
        VertexFormat format = TestUtil.randomVertexFormat();
        mesh.addVertexFormat(format);
        ByteBuffer vertices = mesh.getVertices(format);
        int expectedByteCount = mesh.getVertexCount() * format.getByteCount();
        int actualByteCount = vertices.limit() - vertices.position();
        Assert.assertEquals(expectedByteCount, actualByteCount);
    }

    private void testFaceIndices(EditableMesh mesh) {
        if (mesh.getFaceCount() > 0 && mesh.getVertexCount() > 0) {
            int faceIndex = RANDOM.nextInt(mesh.getFaceCount());
            int indicesIndex = RANDOM.nextInt(3);
            int vertexIndex = RANDOM.nextInt(mesh.getVertexCount());
            mesh.setFaceIndicesIndex(faceIndex, indicesIndex, vertexIndex);
            int actualVertexIndex = mesh.getFaceIndices(faceIndex)[indicesIndex];
            Assert.assertEquals(vertexIndex, actualVertexIndex);
        }
    }

    private void testVertexData(EditableMesh mesh) {
        if (mesh.getVertexCount() > 0 && mesh.getVertexCount() > 0) {
            int vertexIndex = RANDOM.nextInt(mesh.getVertexCount());
            VertexType vertexType = TestUtil.randomVertexType();
            float vertexDatum = RANDOM.nextFloat();
            mesh.setVertexDatum(vertexIndex, vertexType, vertexDatum);
            float actualVertexDatum = mesh.getVertexDatum(vertexIndex, vertexType);
            Assert.assertEquals(vertexDatum, actualVertexDatum, 0);
        }
    }
}
