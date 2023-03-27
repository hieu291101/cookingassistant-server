package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.request.RecipeInput;
import com.lth.cookingassistant.response.RecipeSuggestion;
import org.springframework.web.bind.annotation.*;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class RecipeSuggestResource {

    @PostMapping("/suggestRecipe")
    public RecipeSuggestion suggestRecipe(@RequestBody RecipeInput input) throws Exception {

        // Load the dataset into memory
        Instances data = new Instances(new BufferedReader(new FileReader("D:\\JavaMasterClassWeb\\cookingassistant\\src\\main\\resources\\recipes.arff")));

        // Set the class attribute to the last attribute (recipe name)
        data.setClassIndex(data.numAttributes() - 1);

        // Create a decision tree classifier
        J48 classifier = new J48();
        classifier.buildClassifier(data);

        // Create a new instance with the input data
        Instance instance = new DenseInstance(data.numAttributes());
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            String attributeName = data.attribute(i).name();
            if (input.getIngredients().contains(attributeName)) {
                instance.setValue(i, 1);
            } else {
                instance.setValue(i, 0);
            }
        }
        instance.setValue(data.numAttributes() - 1, "unknown");

        // Use the classifier to predict the recipe to recommend
        double prediction = classifier.classifyInstance(instance);
        String recommendedRecipe = data.classAttribute().value((int) prediction);

        // Return the recommended recipe as a JSON response
        return new RecipeSuggestion(recommendedRecipe);
    }
}
