package org.dannil.simpletexteditor.model;

import com.google.common.io.Files;

public final class Document {

	private String path;
	private String content;
	
	private String extension;
	
	public Document() {
		this.path = null;
		this.extension = null;
		this.content = null;
	}
	
	public Document(final String content) {
		this();
		this.content = content;
	}
	
	public Document(final String path, final String content) {
		this(content);
		this.path = path;
		this.extension = Files.getFileExtension(path);
	}
	
	public Document(final String path, final String extension, final String content) {
		this(path, content);
		this.extension = extension;
	}

	public final String getPath() {
		return this.path;
	}

	public final void setPath(final String path) {
		this.path = path;
	}

	public final String getContent() {
		return this.content;
	}

	public final void setContent(final String content) {
		this.content = content;
	}

	public final String getExtension() {
		return this.extension;
	}

	public final void setExtension(final String extension) {
		this.extension = extension;
	}
	
}
