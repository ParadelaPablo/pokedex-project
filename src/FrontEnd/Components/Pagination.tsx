import React from 'react';

interface PaginationProps {
    currentPage: number;
    totalPages: number;
    onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
    const visiblePages = 5; 
    const startPage = Math.max(1, currentPage - Math.floor(visiblePages / 2)); 
    const endPage = Math.min(totalPages, startPage + visiblePages - 1);

    const pages = Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index); 

    return (
        <div className="w-full overflow-x-auto flex justify-center mt-4">
            <div className="pagination flex space-x-2">

                {currentPage > 1 && (
                    <button
                        onClick={() => onPageChange(currentPage - 1)}
                        className="bg-gray-200 text-gray-700 p-2 rounded-md hover:bg-blue-600 transition"
                    >
                        &lt; Prev
                    </button>
                )}

                {pages.map((page) => (
                    <button
                        key={page}
                        disabled={page === currentPage}
                        onClick={() => onPageChange(page)}
                        className={`pagination-button ${page === currentPage ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'} p-2 rounded-md hover:bg-blue-600 transition`}
                    >
                        {page}
                    </button>
                ))}

                {currentPage < totalPages && (
                    <button
                        onClick={() => onPageChange(currentPage + 1)}
                        className="bg-gray-200 text-gray-700 p-2 rounded-md hover:bg-blue-600 transition"
                    >
                        Next &gt;
                    </button>
                )}
            </div>
        </div>
    );
};

export default Pagination;
