package board.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.board.model.service.BoardService;
import board.board.model.vo.Board;

@WebServlet("/page/board/write")
public class BoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardWriteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardService sv = new BoardService();
		
		String saveDirectory = "/files"; // 웹 서버상의 절대 경로
	
		String encType = "UTF-8";
		int maxSize = 5*1024*1024; // 업로드 파일의 최대 크기 5Mb
		
		try {
			// savaDirectory 폴더가 없다면 생성
			String root = getServletContext().getRealPath("/");
			File path = new File(root + saveDirectory);
			if(!path.exists()) {
				path.mkdirs();
			}
			
		// 저장 saveDirectory에 file 저장됨. 이미 이 순간 이 시점에! 이 코드에서! 업로드한 파일 저장이 됨!
		saveDirectory = getServletContext().getRealPath("/files");;
		MultipartRequest mReq = new MultipartRequest(request, saveDirectory, maxSize, encType, new DefaultFileRenamePolicy());
		// cos.jar를 쓰기에 쓰는 방식임. cos에서 제공하는 메소드 사용.
		
		// 저장된 file의 정보(file 경로+file name)을 읽어오기 --> DB에 저장할 내용임
		String fileName = "";
		Enumeration<?> files = mReq.getFileNames();
		while(files.hasMoreElements()) {
			String name = (String) files.nextElement(); // input type="file" name"xxxxxx"
			// 즉 bfilepath와 bfilepaths
			fileName = mReq.getFilesystemName(name); // 서버에 저장된 파일 이름
			File f1 = mReq.getFile(name); // 서버에 file이 정상적으로 저장되어있는지 다시 읽어와서 확인함.
			if(f1==null) {
				System.out.println("파일 업로드 실패");
			}else {
				System.out.println("파일 업로드 성공 : " + f1.length()); // 파일 크기 확인
			}
		}
				
		Board vo = new Board();
		vo.setId(mReq.getParameter("id"));
		vo.setBsubject(mReq.getParameter("bsubject"));
		vo.setBcontent(mReq.getParameter("bcontent"));
		vo.setBfilePath(fileName);
		vo.setHobbyId(Integer.parseInt(mReq.getParameter("hobbyId")));
		vo.setLocNum(Integer.parseInt(mReq.getParameter("locNum")));
						
		int result = sv.boardWrite(vo); 
		PrintWriter out = response.getWriter();
		if(result>0) { // 정상 등록
			System.out.println("정상 등록");
			out.println("<script> alert('글 등록 완료')</script>");
			out.println("<script>location.href='/page/hobby/hobbyread';</script>");

		} else { // 등록 실패 
			System.out.println("입력 실패");
			String msg = "글 등록 실패";
			out.println("<script> alert('"+msg+"')</script>");
			out.println("<script>history.back();</script>");		
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}