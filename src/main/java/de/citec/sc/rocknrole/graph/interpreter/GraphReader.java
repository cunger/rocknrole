package de.citec.sc.rocknrole.graph.interpreter;

import de.citec.sc.rocknrole.graph.Graph;
import de.citec.sc.rocknrole.graph.interpreter.grammar.DependenciesLexer;
import de.citec.sc.rocknrole.graph.interpreter.grammar.DependenciesParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 *
 * @author cunger
 */
public class GraphReader {
           
    public GraphReader() {
    }
    
    public Graph interpret(String dependency) {
        
        try {
                        
            // Parse dependency string
                        
            InputStream stream = new ByteArrayInputStream(dependency.getBytes(StandardCharsets.UTF_8));
            ANTLRInputStream input = new ANTLRInputStream(stream);
            
            DependenciesLexer  lexer  = new DependenciesLexer(input);
            CommonTokenStream  tokens = new CommonTokenStream(lexer);
            DependenciesParser parser = new DependenciesParser(tokens);
            
            ParseTree tree = parser.graph(); 
            
            // Constructor graph from parse tree 
            
            ParseTreeWalker walker = new ParseTreeWalker();
            GraphConstructor constructor = new GraphConstructor();
            walker.walk(constructor,tree);
            
            return(constructor.graph);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        } 
        
        return null;
    }
    
}