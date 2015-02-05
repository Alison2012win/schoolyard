<%@ page contentType="image/jpeg"
	import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%!Color getRandColor(int fc, int bc) {//Color range
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}%>
<%
	response.reset();
	response.setContentType("image/jpeg");
	//set page No-cache
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("Expires", 0);

	int width = 60, height = 20;
	// Create Image
	BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);

	// get graph context
	Graphics g = image.getGraphics();

	Random random = new Random();

	// set background color
	g.setColor(getRandColor(200, 250));
	g.fillRect(0, 0, width, height);

	// set Font style
	g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

	// draw frame
	//g.setColor(new Color());
	//g.drawRect(0,0,width-1,height-1);

	// draw 155 interfering line
	g.setColor(getRandColor(160, 200));
	for (int i = 0; i < 155; i++) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x, y, x + xl, y + yl);
	}

	// Create String for CheckCode
	String sRand = "";
	String chose = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	for (int i = 0; i < 4; i++) {
		String rand = String.valueOf(chose.charAt(random.nextInt(chose
				.length())));
		sRand += rand;
		// draw CheckCode
		g.setColor(new Color(20 + random.nextInt(110), 20 + random
				.nextInt(110), 20 + random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		g.drawString(rand, 13 * i + 6, 16);
	}

	//set CheckCode in Session
	if (session.getAttribute("rand") != null) {
		session.removeAttribute("rand");
	}
	session.setAttribute("rand", sRand.toLowerCase());

	// activation Graphics
	g.dispose();

	// Write image to page
	ImageIO.write(image, "JPEG", response.getOutputStream());

	out.clear();
	out = pageContext.pushBody();
%>