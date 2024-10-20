package tn.isetsf.presence.webThymeleaf;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isetsf.presence.Repository.LigneAbsenceRepo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AbsenceGraphService {

    @Autowired
    private LigneAbsenceRepo absenceRepository;

    public void generateMostAbsentTeachersGraph() throws IOException {
        // Récupérer les enseignants et le nombre d'absences depuis le repository
        List<Object[]> absencesByEnseignant = absenceRepository.countAbsencesByEnseignantNative();
        List<Object[]> absence =new ArrayList<>();
        for (int i=0;i<5;i++){
            absence.add(absencesByEnseignant.get(i));
        }

        // Créer un dataset pour le graphique
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Ajouter les données au dataset
        for (Object[] row : absence) {
            String enseignant = (String) row[0]; // Nom de l'enseignant
            Long count = ((Number) row[1]).longValue(); // Nombre d'absences
            dataset.addValue(count, "Absences", enseignant);
        }

        // Générer le graphique
        JFreeChart barChart = ChartFactory.createBarChart(
                "Top 5 absences enseignants ",
                "Enseignant",
                "Nombre d'absences",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Appliquer des styles modernes
        barChart.setBackgroundPaint(Color.white);

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(222, 222, 255)); // Fond de la zone de tracé
        plot.setRangeGridlinePaint(Color.black);  // Ligne de grille

        // Personnaliser les barres
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new GradientPaint(0.0f, 0.0f, Color.BLUE, 0.0f, 0.0f, Color.CYAN)); // Couleur en dégradé
        renderer.setShadowVisible(true);  // Ajouter des ombres
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelFont(new Font("Arial", Font.BOLD, 12));

        // Personnaliser les polices
        barChart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.getRangeAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 12));

        // Chemin du fichier image
        File directory = new File("src/main/resources/static/images");
        if (!directory.exists()) {
            directory.mkdirs();  // Crée le répertoire s'il n'existe pas
        }

        File imageFile = new File(directory, "mostAbsentTeachersGraph.png");

        // Vérifier si le fichier existe déjà
        if (imageFile.exists()) {
            // Supprimer le fichier existant avant de le remplacer
            boolean deleted = imageFile.delete();
            if (deleted) {
                System.out.println("Fichier existant supprimé avec succès.");
            } else {
                System.out.println("Échec de la suppression du fichier existant.");
            }
        }

        // Sauvegarder le nouveau graphique sous forme d'image
        ChartUtils.saveChartAsPNG(imageFile, barChart, 300, 300);  // Taille du graphique
        System.out.println("Image du graphique générée et sauvegardée : " + imageFile.getAbsolutePath());
    }

}
