package org.ranksys.recommenders.fm.learner;

import java.util.Arrays;
import java.util.Random;
import org.ranksys.core.index.fast.FastItemIndex;
import org.ranksys.core.index.fast.FastUserIndex;
import org.ranksys.core.preference.fast.FastPreferenceData;
import org.ranksys.javafm.FM;
import org.ranksys.javafm.data.FMData;
import org.ranksys.javafm.learner.FMLearner;
import org.ranksys.recommenders.fm.PreferenceFM;

/**
 *
 * @author Saúl Vargas (Saul@VargasSandoval.es)
 */
public abstract class PreferenceFMLearner<U, I> {

    private final FastUserIndex<U> users;
    private final FastItemIndex<I> items;

    public PreferenceFMLearner(FastUserIndex<U> users, FastItemIndex<I> items) {
        this.users = users;
        this.items = items;
    }

    protected abstract FMLearner<FMData> getLearner();

    protected abstract FMData toFMData(FastPreferenceData<U, I> preferences);

    public void learn(PreferenceFM<U, I> fm, FastPreferenceData<U, I> train) {
        getLearner().learn(fm.getFM(), toFMData(train));
    }

    public void learn(PreferenceFM<U, I> fm, FastPreferenceData<U, I> train, FastPreferenceData<U, I> test) {
        getLearner().learn(fm.getFM(), toFMData(train), toFMData(test));
    }

    public PreferenceFM<U, I> learn(FastPreferenceData<U, I> train, int K, double sdev) {
        FMData fmTrain = toFMData(train);
        FM fm = new FM(fmTrain.numFeatures(), K, new Random(), sdev);

        getLearner().learn(fm, fmTrain);

        PreferenceFM<U, I> prefFm = new PreferenceFM<>(users, items, fm);

        return prefFm;
    }

    public PreferenceFM<U, I> learn(FastPreferenceData<U, I> train, FastPreferenceData<U, I> test, int K, double sdev) {
        FMData fmTrain = toFMData(train);
        FMData fmTest = toFMData(test);
        FM fm = new FM(fmTrain.numFeatures(), K, new Random(), sdev);

        getLearner().learn(fm, fmTrain, fmTest);

        PreferenceFM<U, I> prefFm = new PreferenceFM<>(users, items, fm);

        return prefFm;
    }

    protected double[] vectorise(double v) {
        double[] a = new double[users.numUsers() + items.numItems()];
        Arrays.fill(a, v);

        return a;
    };

}
