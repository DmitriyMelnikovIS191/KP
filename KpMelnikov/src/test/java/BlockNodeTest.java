import com.example.kpmelnikov.parser.BlockNode;
import com.example.kpmelnikov.parser.BlockType;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BlockNodeTest {

    public static BlockNode blockNode;

    @BeforeAll
    static void init(){
        blockNode = new BlockNode(BlockType.End);

    }
    @Test
    void addChildTest() {
        blockNode.addChild(new BlockNode(BlockType.Start));
        Assert.assertEquals(1,blockNode.children.size());
    }

    @Test
    void addParentTest() {
        blockNode.addParent(new BlockNode(BlockType.Process));
        Assert.assertEquals(1,blockNode.parents.size());
    }

    @Test
    void removeChild() {
        blockNode.removeChild(new BlockNode(BlockType.End));
        Assert.assertEquals(0,blockNode.children.size());
    }

    @Test
    void removeParent() {
        blockNode.removeChild(new BlockNode(BlockType.End));
        Assert.assertEquals(0,blockNode.parents.size());
    }
}