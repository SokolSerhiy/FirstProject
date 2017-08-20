package ua.model.resource;

import java.util.ArrayList;
import java.util.List;

public abstract class ResourceSupport {

	private List<Link> links = new ArrayList<>();

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
