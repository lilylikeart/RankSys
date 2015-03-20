/* 
 * Copyright (C) 2015 Information Retrieval Group at Universidad Autonoma
 * de Madrid, http://ir.ii.uam.es
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.uam.eps.ir.ranksys.novdiv.distance;

import es.uam.eps.ir.ranksys.core.IdObject;
import es.uam.eps.ir.ranksys.core.feature.FeatureData;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Saúl Vargas (saul.vargas@uam.es)
 */
public class JaccardFeatureItemDistanceModel<I, F> extends FeatureItemDistanceModel<I, F, Double> {

    public JaccardFeatureItemDistanceModel(FeatureData<I, F, Double> featureData) {
        super(featureData);
    }

    @Override
    public double dist(Stream<IdObject<F, Double>> features1, Stream<IdObject<F, Double>> features2) {
        Object2DoubleMap<F> auxMap = new Object2DoubleOpenHashMap<>();
        auxMap.defaultReturnValue(0.0);
        double[] comps = {0.0, 0.0, 0.0};

        features1.forEach(fv -> {
            auxMap.put(fv.id, fv.v);
            comps[0] += fv.v * fv.v;
        });
        
        if (comps[0] == 0) {
            return Double.NaN;
        }

        features2.forEach(fv -> {
            comps[1] += fv.v * fv.v;
            comps[2] += fv.v * auxMap.getDouble(fv.id);
        });
        
        if (comps[1] == 0) {
            return Double.NaN;
        }
        
        return 1 - comps[2] / (comps[0] + comps[1] - comps[2]);
    }

}