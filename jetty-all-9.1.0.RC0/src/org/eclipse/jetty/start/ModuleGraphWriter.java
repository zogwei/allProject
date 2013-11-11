//
//  ========================================================================
//  Copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.start;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Generate a graphviz dot graph of the modules found
 */
public class ModuleGraphWriter
{
    private String colorModuleBg;
    private String colorEnabledBg;
    private String colorTransitiveBg;
    private String colorCellBg;
    private String colorHeaderBg;
    private String colorModuleFont;

    public ModuleGraphWriter()
    {
        colorModuleBg = "#B8FFB8";
        colorEnabledBg = "#66FFCC";
        colorTransitiveBg = "#66CC66";
        colorCellBg = "#FFFFFF80";
        colorHeaderBg = "#00000020";
        colorModuleFont = "#888888";
    }

    public void config(Properties props)
    {
        String prefix = "jetty.graph.";
        colorModuleBg = getProperty(props,prefix + "color.module.bg",colorModuleBg);
        colorEnabledBg = getProperty(props,prefix + "color.enabled.bg",colorEnabledBg);
        colorTransitiveBg = getProperty(props,prefix + "color.transitive.bg",colorTransitiveBg);
        colorCellBg = getProperty(props,prefix + "color.cell.bg",colorCellBg);
        colorHeaderBg = getProperty(props,prefix + "color.header.bg",colorHeaderBg);
        colorModuleFont = getProperty(props,prefix + "color.font",colorModuleFont);
    }

    private String getProperty(Properties props, String key, String defVal)
    {
        String val = props.getProperty(key,defVal);
        if (val == null)
        {
            return defVal;
        }
        val = val.trim();
        if (val.length() <= 0)
        {
            return defVal;
        }
        return val;
    }

    public void write(Modules modules, File outputFile) throws IOException
    {
        try (FileWriter writer = new FileWriter(outputFile,false); PrintWriter out = new PrintWriter(writer);)
        {
            writeHeaderMessage(out,outputFile);

            out.println();
            out.println("digraph modules {");

            // Node Style
            out.println("  node [color=gray, style=filled, shape=rectangle];");
            out.println("  node [fontname=\"Verdana\", size=\"20,20\"];");
            // Graph Style
            out.println("  graph [");
            out.println("    concentrate=false,");
            out.println("    fontname=\"Verdana\",");
            out.println("    fontsize = 20,");
            out.println("    rankdir = LR,");
            out.println("    ranksep = 1.5,");
            out.println("    nodesep = .5,");
            out.println("    style = bold,");
            out.println("    labeljust = l,");
            out.println("    label = \"Jetty Modules\",");
            out.println("    ssize = \"20,40\"");
            out.println("  ];");

            List<Module> enabled = modules.resolveEnabled();

            // Module Nodes
            writeModules(out,modules,enabled);

            // Module Relationships
            writeRelationships(out,modules,enabled);

            out.println("}");
            out.println();
        }
    }

    private void writeHeaderMessage(PrintWriter out, File outputFile)
    {
        out.println("/*");
        out.println(" * GraphViz Graph of Jetty Modules");
        out.println(" * ");
        out.println(" * Jetty: http://eclipse.org/jetty/");
        out.println(" * GraphViz: http://graphviz.org/");
        out.println(" * ");
        out.println(" * To Generate Graph image using graphviz:");
        String filename = outputFile.getName();
        String basename = filename.substring(0,filename.indexOf('.'));
        out.printf(" *   $ dot -Tpng -Goverlap=false -o %s.png %s%n",basename,filename);
        out.println(" */");
    }

    private void writeModuleDetailHeader(PrintWriter out, String header)
    {
        writeModuleDetailHeader(out,header,1);
    }

    private void writeModuleDetailHeader(PrintWriter out, String header, int count)
    {
        out.printf("  <TR>");
        out.printf("<TD BGCOLOR=\"%s\" ALIGN=\"LEFT\"><I>",colorHeaderBg);
        out.printf("%s%s</I></TD>",header,count > 1?"s":"");
        out.println("</TR>");
    }

    private void writeModuleDetailLine(PrintWriter out, String line)
    {
        out.printf("  <TR>");
        StringBuilder escape = new StringBuilder();
        for(char c: line.toCharArray()) {
            switch(c) {
                case '<': escape.append("&lt;"); break;
                case '>': escape.append("&gt;"); break;
                default:
                    escape.append(c);
                    break;
            }
        }
        
        out.printf("<TD BGCOLOR=\"%s\" ALIGN=\"LEFT\">%s</TD></TR>%n",colorCellBg,escape.toString());
    }

    private void writeModuleNode(PrintWriter out, Module module, boolean resolved)
    {
        String color = colorModuleBg;
        if (module.isEnabled())
        {
            // specifically enabled by config
            color = colorEnabledBg;
        }
        else if (resolved)
        {
            // enabled by transitive reasons
            color = colorTransitiveBg;
        }

        out.printf("  \"%s\" [ color=\"%s\" label=<",module.getName(),color);
        out.printf("<TABLE BORDER=\"0\" CELLBORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">%n");
        out.printf("  <TR><TD ALIGN=\"LEFT\"><B>%s</B></TD></TR>%n",module.getName());

        if (module.isEnabled())
        {
            writeModuleDetailHeader(out,"ENABLED");
            for (String source : module.getSources())
            {
                writeModuleDetailLine(out,"via: " + source);
            }
        }
        else if (resolved)
        {
            writeModuleDetailHeader(out,"TRANSITIVE");
        }

        if (!module.getXmls().isEmpty())
        {
            List<String> xmls = module.getXmls();
            writeModuleDetailHeader(out,"XML",xmls.size());
            for (String xml : xmls)
            {
                writeModuleDetailLine(out,xml);
            }
        }

        if (!module.getLibs().isEmpty())
        {
            List<String> libs = module.getLibs();
            writeModuleDetailHeader(out,"LIB",libs.size());
            for (String lib : libs)
            {
                writeModuleDetailLine(out,lib);
            }
        }

        if (!module.getInitialise().isEmpty())
        {
            List<String> inis = module.getInitialise();
            writeModuleDetailHeader(out,"INI Template",inis.size());
        }

        out.printf("</TABLE>>];%n");
    }

    private void writeModules(PrintWriter out, Modules allmodules, List<Module> enabled)
    {
        out.println();
        out.println("  /* Modules */");
        out.println();

        out.println("  node [ labeljust = l ];");

        for (int depth = 0; depth <= allmodules.getMaxDepth(); depth++)
        {
            out.println();
            Collection<Module> depthModules = allmodules.getModulesAtDepth(depth);
            if (depthModules.size() > 0)
            {
                out.printf("  /* Level %d */%n",depth);
                out.println("  { rank = same;");
                for (Module module : depthModules)
                {
                    boolean resolved = enabled.contains(module);
                    writeModuleNode(out,module,resolved);
                }
                out.println("  }");
            }
        }
    }

    private void writeRelationships(PrintWriter out, Modules modules, List<Module> enabled)
    {
        for (Module module : modules)
        {
            for (Module parent : module.getParentEdges())
            {
                out.printf("    \"%s\" -> \"%s\";%n",module.getName(),parent.getName());
            }
        }
    }
}
