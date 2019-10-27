package com.example.testcorenlp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.JSONOutputter;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.d("onCreate", "maxMemory:" + Long.toString(maxMemory));

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, ,pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        String text = "What is the Weather in Bangalore right now?";

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                System.out.println(String.format("Print: word: [%s] pos: [%s] ne: [%s]", word, pos, ne));
            }
        }

        JSONOutputter jsonOutputter = new JSONOutputter();
        try {
            Log.d("JSONOutput", JSONOutputter.jsonPrint(document));
            JSONOutputter.jsonPrint(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
