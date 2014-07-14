package com.github.alex_moon.trim.correlation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.alex_moon.trim.term.Term;

public class Controller {
    Map<Term, Map<Term, Correlation>> correlations;
    
    public List<Correlation> getCorrelations(Term term) {
        Map<Term, Correlation> correlationsMap = correlations.get(term);
        return new ArrayList<Correlation>(correlationsMap.values());
    }
    
    public void addCorrelation(Correlation correlation) {
        Term a = correlation.getPrimaryTerm();
        Term b = correlation.getSecondaryTerm();
        if (correlations.containsKey(a)) {
            correlations.get(a).put(b, correlation);
            return;
        }
        if (correlations.containsKey(b)) {
            correlations.get(b).put(a, correlation);
            return;
        }
        Map<Term, Correlation> correlationMap = new HashMap<Term, Correlation>();
        correlationMap.put(b, correlation);
        correlations.put(a, correlationMap);
    }
    
    public void updateCorrelation(Term a, Term b) {
        Correlation correlation = correlations.get(a).get(b);
        if (correlation == null) {
            correlation = correlations.get(b).get(a);
        }
        if (correlation == null) {
            correlation = new Correlation(a, b, 1.0);
            addCorrelation(correlation);
        }
        correlation.update();
        // @todo dynamodb
    }
}
