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

import java.util.concurrent.LinkedBlockingQueue;

public class Main {

  static LinkedBlockingQueue<String> rawWikiTextQueue = new LinkedBlockingQueue<String>();
  static boolean rawTextParsingDone = false;

  static class RawTextFilter implements IArticleFilter {
    @Override
    public void process(WikiArticle page, Siteinfo siteinfo) throws SAXException {
      if ( page.isMain() ) {
        rawWikiTextQueue.add(page.toString());
      }
    }
  }

  static class PlainTextEmitter implements Runnable {
    static final PlainTextConverter CONVERTER = new PlainTextConverter();
    static final WikiModel WIKI_MODEL = new WikiModel("/${image}", "$/{title}");
    public void run() {
      while ( !rawTextParsingDone || !rawWikiTextQueue.isEmpty() ) {
        try {
          String rawWikiText = rawWikiTextQueue.take();
          String plainText = WIKI_MODEL.render(CONVERTER, rawWikiText);
          System.out.println(plainText);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  public static void main(String[] args) throws IOException, SAXException {
    Thread plainTextEmitter = new Thread(new PlainTextEmitter());
    plainTextEmitter.start();
    new WikiXMLParser(new File(args[0]), new RawTextFilter()).parse();
    rawTextParsingDone = true;
  }
}
