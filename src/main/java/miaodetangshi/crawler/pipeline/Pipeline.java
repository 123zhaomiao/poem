package miaodetangshi.crawler.pipeline;

import miaodetangshi.crawler.common.Page;

/**
 * 清洗
 */
public interface Pipeline {
    void pipeline(Page page);
}
