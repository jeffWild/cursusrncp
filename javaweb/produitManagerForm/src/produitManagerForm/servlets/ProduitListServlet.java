package produitManagerForm.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import produitManagerForm.metier.Produit;
import produitManagerForm.utils.ProduitDAO;

/**
 * Servlet implementation class ProduitListServlet
 */
public class ProduitListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProduitListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// getAttribute renvoie un object, je dois le convertir en (List<Produit>)
/*		List<Produit> produits = (List<Produit>)getServletContext().getAttribute("produits");
		
		List<Produit> filteredList = new ArrayList<>(produits);
		
		request.setAttribute("produits", filteredList);
	*/
		ProduitDAO produitdao = (ProduitDAO)getServletContext().getAttribute("produitDAO");
		// je recupere le tri choisi
		String tri = request.getParameter("tri");
		if (tri == null)
			tri = "0"; // s'il ny en a pas, on choisi pas de tri par defaut
		// je liste les produits en indiquant au doa le choix du tri
		List<Produit> produits = produitdao.findAll(Integer.parseInt(tri));
		
		request.setAttribute("produits", produits);
		getServletContext().getRequestDispatcher("/liste.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
