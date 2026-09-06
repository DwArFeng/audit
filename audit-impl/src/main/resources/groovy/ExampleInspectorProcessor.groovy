import com.dwarfeng.audit.impl.handler.inspector.groovy.Processor
import com.dwarfeng.audit.stack.handler.Inspector

/**
 * 示例审计器处理器。
 *
 * <p>
 * 该处理器不执行任何操作。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@SuppressWarnings(['GrPackage', 'unused'])
class ExampleInspectorProcessor implements Processor {

    @Override
    void inspect(Inspector.Context context) {
    }
}
