package vindvaki.wikitext.app;

import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiXMLParser;
import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class Main {
  static class PlainTextFilter implements IArticleFilter {
    @Override
    public void process(WikiArticle page, Siteinfo siteinfo) throws SAXException {
       if ( page.isMain() ) {
        String rawWikiText = page.getText();
        WikiModel wikiModel = new WikiModel("/${image}", "$/{title}");
        PlainTextConverter converter = new PlainTextConverter();
        String plainText = wikiModel.render(converter, rawWikiText);
        System.out.println(plainText);
      }
    }
  }
  
  public static void main(String[] args) throws IOException, SAXException {
    new WikiXMLParser(new File(args[0]), new PlainTextFilter()).parse();
  }
}
