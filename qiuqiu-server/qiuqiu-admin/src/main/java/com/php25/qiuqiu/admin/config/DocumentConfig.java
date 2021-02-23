package com.php25.qiuqiu.admin.config;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

/**
 * @author penghuiping
 * @date 2021/2/3 16:10
 */
@Profile("local")
@Configuration
public class DocumentConfig {

    @Value("${document.project-path}")
    private String projectPath;

    @PostConstruct
    public void init() {
        DocsConfig config = new DocsConfig();
        config.setProjectPath(projectPath);
        config.setProjectName("wx-company-admin");
        config.setApiVersion("V1.0");
        config.setDocsPath(projectPath + "/target");
        config.setAutoGenerate(Boolean.FALSE);
        config.addPlugin(new MarkdownDocPlugin());
        Docs.buildHtmlDocs(config);
    }
}
